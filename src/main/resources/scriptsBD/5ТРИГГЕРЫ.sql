-- Триггер для расчета стоимости грузоперевозки
CREATE OR REPLACE FUNCTION calculate_transportation_cost()
RETURNS TRIGGER AS $$
DECLARE
    departure_city TEXT;
    arrival_city TEXT;
BEGIN
    -- Получение города отправления для контракта
    SELECT city 
    INTO departure_city
    FROM destinationpoints dp
    JOIN contracts c ON c.id = dp.contract_id
    WHERE c.id = NEW.contract_id AND dp.type = 'Отправление'
    LIMIT 1;

    -- Получение города прибытия для контракта
    SELECT city 
    INTO arrival_city
    FROM destinationpoints dp
    JOIN contracts c ON c.id = dp.contract_id
    WHERE c.id = NEW.contract_id AND dp.type = 'Прибытие'
    LIMIT 1;

    -- Если города отправления и прибытия найдены, то обновляем стоимость
    IF departure_city IS NOT NULL AND arrival_city IS NOT NULL THEN
        UPDATE contracts AS c
        SET cost = (
            CASE 
                WHEN departure_city like arrival_city THEN 1000
                ELSE 2000
            END * (COALESCE(NEW.weight, 0) / 100) * COALESCE(NEW.volume, 0)
        )
        WHERE c.id = NEW.contract_id;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Триггер для INSERT, UPDATE, DELETE на таблицу cargos
CREATE TRIGGER update_transportation_cost
AFTER INSERT OR UPDATE OR DELETE ON cargos
FOR EACH ROW
EXECUTE FUNCTION calculate_transportation_cost();


CREATE TRIGGER update_transportation_cost
AFTER INSERT ON cargos
FOR EACH ROW
EXECUTE FUNCTION calculate_transportation_cost();


-- Триггер для предотвращения добавления рейса водителю, если у него уже есть активный
CREATE OR REPLACE FUNCTION prevent_duplicate_driver_trip()
RETURNS TRIGGER AS $$
DECLARE
    active_trip_count INTEGER;
BEGIN
    SELECT COUNT(*)
    INTO active_trip_count
    FROM contracts
    WHERE driver_id = NEW.driver_id
      AND conclusiondate IS NULL;

    IF active_trip_count > 0 THEN
        RAISE EXCEPTION 'Driver already has an active trip. Cannot assign another trip.';
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER check_driver_trip
BEFORE INSERT ON contracts
FOR EACH ROW
EXECUTE FUNCTION prevent_duplicate_driver_trip();

-- Триггер для добавления к стоимости договора стоимости дополнительных услуг
CREATE OR REPLACE FUNCTION update_contract_cost_with_services()
RETURNS TRIGGER AS $$
BEGIN
    IF TG_OP = 'DELETE' THEN
        RAISE NOTICE 'DELETE operation: OLD.contractid = %', OLD.contractid;
        -- Сначала вычтем стоимость удаленной услуги
        UPDATE contracts AS c
        SET cost = c.cost - (
            SELECT COALESCE(ads.cost, 0)
            FROM additionalservices AS ads
            WHERE ads.id = OLD.additionalserviceid
        )
        WHERE c.id = OLD.contractid;
        RAISE NOTICE 'Updated cost after DELETE: %', (SELECT cost FROM contracts WHERE id = OLD.contractid);
        
    ELSIF TG_OP = 'INSERT' THEN
        RAISE NOTICE 'INSERT operation: NEW.contractid = %', NEW.contractid;
        -- Затем добавим стоимость новой услуги
        UPDATE contracts AS c
        SET cost = c.cost + (
            SELECT COALESCE(ads.cost, 0)
            FROM additionalservices AS ads
            WHERE ads.id = NEW.additionalserviceid
        )
        WHERE c.id = NEW.contractid;
        RAISE NOTICE 'Updated cost after INSERT: %', (SELECT cost FROM contracts WHERE id = NEW.contractid);
        
    ELSIF TG_OP = 'UPDATE' THEN
        RAISE NOTICE 'UPDATE operation: OLD.contractid = %, NEW.contractid = %', OLD.contractid, NEW.contractid;
        -- Сначала вычтем стоимость старой услуги
        UPDATE contracts AS c
        SET cost = c.cost - (
            SELECT COALESCE(ads.cost, 0)
            FROM additionalservices AS ads
            WHERE ads.id = OLD.additionalserviceid
        )
        WHERE c.id = OLD.contractid;
        RAISE NOTICE 'Updated cost after UPDATE (old): %', (SELECT cost FROM contracts WHERE id = OLD.contractid);

        -- Затем добавим стоимость новой услуги
        UPDATE contracts AS c
        SET cost = c.cost + (
            SELECT COALESCE(ads.cost, 0)
            FROM additionalservices AS ads
            WHERE ads.id = NEW.additionalserviceid
        )
        WHERE c.id = NEW.contractid;
        RAISE NOTICE 'Updated cost after UPDATE (new): %', (SELECT cost FROM contracts WHERE id = NEW.contractid);
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER add_service_cost_to_contract
AFTER INSERT OR UPDATE OR DELETE ON contract_additionalservices
FOR EACH ROW
EXECUTE FUNCTION update_contract_cost_with_services();

-- аудит
-- Создание триггерной функции для аудита (для оператора)
CREATE OR REPLACE FUNCTION audit_function_statement()
RETURNS TRIGGER AS $$
DECLARE
    v_old_data JSON;
    v_new_data JSON;
BEGIN
    IF TG_OP = 'INSERT' THEN
        v_new_data := json_agg(row_to_json(NEW))::JSON;
        v_old_data := NULL;
    ELSIF TG_OP = 'UPDATE' THEN
        v_new_data := json_agg(row_to_json(NEW))::JSON;
        v_old_data := json_agg(row_to_json(OLD))::JSON;
    ELSIF TG_OP = 'DELETE' THEN
        v_new_data := NULL;
        v_old_data := json_agg(row_to_json(OLD))::JSON;
    END IF;

    INSERT INTO audit_log (table_name, operation, changed_by, change_timestamp, old_data, new_data)
    VALUES (TG_TABLE_NAME, TG_OP, current_user, CURRENT_TIMESTAMP, v_old_data, v_new_data);

    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

-- Создание триггера на оператор для всех таблиц
CREATE TRIGGER audit_all_tables_statement
AFTER INSERT OR UPDATE OR delete 
ON contacts
FOR EACH STATEMENT
EXECUTE FUNCTION audit_function_statement();

CREATE TRIGGER audit_all_tables_statement
AFTER INSERT OR UPDATE OR DELETE
ON autoparks
FOR EACH STATEMENT
EXECUTE FUNCTION audit_function_statement();

CREATE TRIGGER audit_all_tables_statement
AFTER INSERT OR UPDATE OR DELETE
ON cars
FOR EACH STATEMENT
EXECUTE FUNCTION audit_function_statement();

CREATE TRIGGER audit_all_tables_statement
AFTER INSERT OR UPDATE OR DELETE
ON additionalservices
FOR EACH STATEMENT
EXECUTE FUNCTION audit_function_statement();

CREATE TRIGGER audit_all_tables_statement
AFTER INSERT OR UPDATE OR DELETE
ON customers
FOR EACH STATEMENT
EXECUTE FUNCTION audit_function_statement();

CREATE TRIGGER audit_all_tables_statement
AFTER INSERT OR UPDATE OR DELETE
ON class_cargos
FOR EACH STATEMENT
EXECUTE FUNCTION audit_function_statement();

CREATE TRIGGER audit_all_tables_statement
AFTER INSERT OR UPDATE OR DELETE
ON cargos
FOR EACH STATEMENT
EXECUTE FUNCTION audit_function_statement();

CREATE TRIGGER audit_all_tables_statement
AFTER INSERT OR UPDATE OR DELETE
ON jobs
FOR EACH STATEMENT
EXECUTE FUNCTION audit_function_statement();

CREATE TRIGGER audit_all_tables_statement
AFTER INSERT OR UPDATE OR DELETE
ON employees
FOR EACH STATEMENT
EXECUTE FUNCTION audit_function_statement();

CREATE TRIGGER audit_all_tables_statement
AFTER INSERT OR UPDATE OR DELETE
ON destinationpoints
FOR EACH STATEMENT
EXECUTE FUNCTION audit_function_statement();

CREATE TRIGGER audit_all_tables_statement
AFTER INSERT OR UPDATE OR DELETE
ON contracts
FOR EACH STATEMENT
EXECUTE FUNCTION audit_function_statement();

CREATE TRIGGER audit_all_tables_statement
AFTER INSERT OR UPDATE OR DELETE
ON contract_additionalservices
FOR EACH STATEMENT
EXECUTE FUNCTION audit_function_statement();



-- Создание функции для обновления статуса договора
CREATE OR REPLACE FUNCTION update_contract_status()
RETURNS TRIGGER AS $$
DECLARE
    contracts_id INTEGER;
    all_delivered BOOLEAN;
    all_cancelled BOOLEAN;
BEGIN
    -- Получаем ID договора, для которого вызван триггер
    contracts_id := NEW.contract_id;

    -- Проверяем, все ли точки назначения в договоре доставлены
    SELECT
        CASE
            WHEN COUNT(*) = SUM(CASE WHEN status = 'Доставлен' THEN 1 ELSE 0 END) THEN TRUE
            ELSE FALSE
        END
    INTO
        all_delivered
    FROM
        destinationpoints
    WHERE
        contracts_id = NEW.contract_id;

    -- Проверяем, все ли точки назначения в договоре отменены
    SELECT
        CASE
            WHEN COUNT(*) = SUM(CASE WHEN status = 'Отменен' THEN 1 ELSE 0 END) THEN TRUE
            ELSE FALSE
        END
    INTO
        all_cancelled
    FROM
        destinationpoints
    WHERE
        contracts_id = NEW.contract_id;

    -- Обновляем статус договора в зависимости от результатов проверок
    IF all_delivered THEN
        UPDATE contracts SET status = 'Выполнен' WHERE id = contracts_id;
    ELSIF all_cancelled THEN
        UPDATE contracts SET status = 'Отменен' WHERE id = contracts_id;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Создание триггера, который вызывает функцию при вставке новой точки назначения
CREATE TRIGGER update_contract_status_trigger
AFTER INSERT OR UPDATE ON destinationpoints
FOR EACH ROW
EXECUTE FUNCTION update_contract_status();
