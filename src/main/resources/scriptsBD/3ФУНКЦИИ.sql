-- функция для вывода активного рейсы для определенного водителя
CREATE OR REPLACE FUNCTION get_active_trip(driver_ids INT)
RETURNS TABLE (
    contracts_number INT,
    driver_fullname TEXT,
    client_fullname TEXT, 
    conclusion_date DATE
) AS $$
BEGIN
    RETURN QUERY
    SELECT 
        ct.id AS contract_number,
        CONCAT(drv.lastname, ' ', drv.firstname, ' ', COALESCE(drv.patronymic, '')) AS driver_fullname,
        CONCAT(cl.lastname, ' ', cl.firstname, ' ', COALESCE(cl.patronymic, '')) AS client_fullname,
        ct.conclusiondate AS conclusion_date
    FROM 
        contracts ct
    JOIN 
        employees drv ON ct.driver_id = drv.id
    JOIN 
        customers cl ON ct.customer_id = cl.id
    WHERE 
        ct.driver_id = driver_ids
        AND ct.status = 'В работе'
        AND EXISTS (
            SELECT 1
            FROM cargos crg
            WHERE crg.contract_id = ct.id
        )
    LIMIT 1;
END;
$$ LANGUAGE plpgsql;

-- функция для создания декларации на грузы
CREATE OR REPLACE FUNCTION generate_cargo_declaration(contracts_id INT)
RETURNS TABLE (
    cargo_name CHARACTER VARYING(255),
    cargo_volume DECIMAL,
    cargo_weight DECIMAL,
    cargo_description TEXT
) AS $$
BEGIN
    RETURN QUERY
    SELECT 
        crg.name AS cargo_name,
        crg.volume AS cargo_volume,
        crg.weight AS cargo_weight,
        COALESCE(cc.description, 'Нет описания') AS cargo_description
    FROM 
        contracts c
    JOIN 
        cargos crg ON crg.contract_id = c.id
    LEFT JOIN 
        class_cargos cc ON crg.class_cargos_id = cc.id
    WHERE 
        c.id = contracts_id;
END;
$$ LANGUAGE plpgsql;

-- функция просмотра грузов в определенном договоре 
CREATE OR REPLACE FUNCTION view_cargos_in_contract(contracts_id INT)
RETURNS TABLE (
    cargos_id INT,
    cargos_name CHARACTER VARYING(255),
    weight NUMERIC(10, 2),
    volume NUMERIC(10, 2),
    cargo_class CHARACTER VARYING(255)
) AS $$
BEGIN
    RETURN QUERY
    SELECT
        c.id AS cargos_id,
        c.name AS cargos_name,
        c.weight,
        c.volume,
        cc.name AS cargo_class
    FROM cargos c
    INNER JOIN class_cargos cc ON c.class_cargos_id = cc.id
    WHERE c.contract_id = contracts_id;
END;
$$ LANGUAGE plpgsql;

-- функция для просмотра активных договоров
CREATE OR REPLACE FUNCTION view_active_contract_info()
RETURNS TABLE (
    contract_number INT,
    manager_fullname TEXT,
    customer_fullname TEXT,
    conclusion_date DATE
) AS $$
BEGIN
    RETURN QUERY
    SELECT
        c.id AS contract_number,
        CONCAT(e.lastname, ' ', e.firstname) AS manager_fullname,
        CONCAT(ct.lastname, ' ', ct.firstname) AS customer_fullname,
        c.conclusiondate AS conclusion_date
    FROM contracts c
    INNER JOIN employees e ON c.manager_id = e.id
    INNER JOIN customers ct ON c.customer_id = ct.id
    WHERE c.status = 'В работе'
    GROUP BY
        c.id,
        e.lastname, e.firstname,
        ct.lastname, ct.firstname
    HAVING COUNT(c.id) > 0;
END;
$$ LANGUAGE plpgsql;

-- функция для простора точек назначения
CREATE OR REPLACE FUNCTION get_contract_destination_points(contract_id_in INT)
RETURNS TABLE (
    destination_point_id INT,
    type type_destinationpoints,
    city VARCHAR,
    address VARCHAR,
    arrivaldate DATE,
    status status_destination
)
LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY
    SELECT 
        dp.id,
        dp.type,
        dp.city AS city,
        dp.address,
        dp.arrivaldate,
        dp.status
    FROM 
        destinationpoints dp
    JOIN 
        contracts c ON dp.contract_id = c.id
    WHERE 
        c.id = contract_id_in;
END;
$$;

-- функция для изменения статуса точки назначения
CREATE OR REPLACE FUNCTION update_destination_status(
    destination_id INT,
    new_status status_destination
) RETURNS VOID AS $$
BEGIN
    UPDATE destinationpoints
    SET status = new_status
    WHERE id = destination_id;
   
    UPDATE destinationpoints
    SET arrivaldate = current_date
    WHERE id = destination_id;
END;
$$ LANGUAGE plpgsql;


-- функция для расчета предварительной стоимости
CREATE OR REPLACE FUNCTION calculate_preliminary_cost(city_from VARCHAR(100), city_to VARCHAR(100), weight NUMERIC, volume NUMERIC)
RETURNS NUMERIC AS $$
DECLARE
    base_cost NUMERIC;
    preliminary_cost NUMERIC;
BEGIN
    IF city_from = city_to THEN
        base_cost := 10;
    ELSE
        base_cost := 20;
    END IF;
    preliminary_cost := base_cost * (weight / 100) * volume;
    RETURN preliminary_cost;
END;
$$ LANGUAGE plpgsql;

-- функция для вывода полной информации об определенном договоре
CREATE OR REPLACE FUNCTION get_contracts_info(contract_id INT)
RETURNS TABLE (
    contracts_id INT,
    conclusion_date DATE,
    cost NUMERIC(10, 2),
    customer_fullname TEXT,
    manager_fullname TEXT,
    driver_fullname TEXT,
    car_licenseplate CHARACTER VARYING(10),
    car_model CHARACTER VARYING(100),
    car_brand CHARACTER VARYING(100),
    destinationpoints_info TEXT,
    additional_services CHARACTER VARYING[]
)
LANGUAGE plpgsql
AS $$
DECLARE
    dp_info TEXT;
BEGIN
    SELECT
        ARRAY_TO_STRING(
            ARRAY(
                SELECT 
                    'Type: ' || dp.type ||
                    ', City: ' || dp.city ||
                    ', Address: ' || dp.address ||
                    ', Arrival Date: ' || dp.arrivaldate::TEXT ||
                    ', Status: ' || dp.status
                FROM destinationpoints dp
                WHERE dp.contract_id = c.id
                ORDER BY dp.id
            ),
            '; '
        )
    INTO dp_info
    FROM contracts c
    WHERE c.id = contract_id;

    RETURN QUERY
    SELECT
        c.id AS contract_id,
        c.conclusiondate AS conclusion_date,
        c.cost,
        CONCAT(cust.firstname, ' ', cust.lastname) AS customer_fullname,
        CONCAT(mgr.firstname, ' ', mgr.lastname) AS manager_fullname,
        CONCAT(drv.firstname, ' ', drv.lastname) AS driver_fullname,
        car.licenseplate AS car_licenseplate,
        car.model AS car_model,
        car.brand AS car_brand,
        dp_info AS destinationpoints_info,
        (
            SELECT ARRAY_AGG(asv.name)
            FROM contract_additionalservices cas
            JOIN additionalservices asv ON cas.additionalserviceid = asv.id
            WHERE cas.contractid = c.id
        ) AS additional_services
    FROM contracts c
    LEFT JOIN customers cust ON c.customer_id = cust.id
    LEFT JOIN employees mgr ON c.manager_id = mgr.id
    LEFT JOIN employees drv ON c.driver_id = drv.id
    LEFT JOIN cars car ON c.car_id = car.id
    WHERE c.id = contract_id
    GROUP BY
        c.id,
        c.conclusiondate,
        c.cost,
        cust.firstname,
        cust.lastname,
        mgr.firstname,
        mgr.lastname,
        drv.firstname,
        drv.lastname,
        car.licenseplate,
        car.model,
        car.brand;
END;
$$;

-- функция для отчета какой сотрудник на какую стоимость оформил договор
CREATE OR REPLACE FUNCTION contracts_summary_for_period(start_date DATE, end_date DATE)
RETURNS TABLE (
    manager_fullname TEXT,
    contracts_count BIGINT,
    total_amount NUMERIC(10, 2)
) AS $$
BEGIN
    RETURN QUERY
    SELECT
        CONCAT(e.lastname, ' ', e.firstname) AS manager_fullname,
        COUNT(c.id) AS contracts_count,
        COALESCE(SUM(c.cost), 0) AS total_amount
    FROM contracts c
    INNER JOIN employees e ON c.manager_id = e.id
    WHERE c.conclusiondate BETWEEN start_date AND end_date
    GROUP BY e.id, e.lastname, e.firstname;
END;
$$ LANGUAGE plpgsql;

-- функция для отчета какой водитель сколько договоров выполнил и какое время он был в рейсах
CREATE OR REPLACE FUNCTION driver_performance_for_period(start_date DATE, end_date DATE)
RETURNS TABLE (
    driver_fullname TEXT,
    contracts_count BIGINT,
    hours_on_routes NUMERIC
) AS $$
BEGIN
    RETURN QUERY
    SELECT
        CONCAT(e.lastname, ' ', e.firstname) AS driver_fullname,
        COUNT(c.id) AS contracts_count,
        COALESCE(
            SUM(
                EXTRACT(HOUR FROM (al.change_timestamp - (al.old_data->>'departuretime')::timestamp))
            ),
            0
        ) AS hours_on_routes
    FROM contracts c
    INNER JOIN employees e ON c.driver_id = e.id
    LEFT JOIN audit_log al ON al.table_name = 'contracts' 
                            AND al.operation = 'UPDATE'
                            AND al.old_data->>'driver_id' = e.id::TEXT
                            AND al.change_timestamp BETWEEN start_date AND end_date
    WHERE c.conclusiondate BETWEEN start_date AND end_date
    GROUP BY e.id, e.lastname, e.firstname;
END;
$$ LANGUAGE plpgsql;

-- функция для авторизации
CREATE OR REPLACE FUNCTION authorization(login_in CHARACTER VARYING, password_in CHARACTER VARYING)
RETURNS TABLE (
	id INT,
	job_name CHARACTER VARYING
)	
AS $$
BEGIN
	RETURN QUERY
    (SELECT e.id, j.name
    FROM employees e
    LEFT JOIN jobs j ON e.job_id = j.id
    WHERE e.login = login_in AND e.password = password_in);
END;
$$ LANGUAGE plpgsql;

-- функция для просмотра услуг в договоре
CREATE OR REPLACE FUNCTION get_additional_services(contract_id INT)
RETURNS TABLE (
    name character varying,
    cost NUMERIC(10, 2),
    description TEXT
) AS $$
BEGIN
    RETURN QUERY
    SELECT 
        asv.name, 
        asv.cost, 
        asv.description
    FROM 
        contract_additionalservices cas
    JOIN 
        additionalservices asv ON cas.additionalserviceid = asv.id
    WHERE 
        cas.contractid = contract_id;
END;
$$ LANGUAGE plpgsql;


-- процедура для создания резервной копии 
CREATE OR REPLACE PROCEDURE create_database_backup(destination_directory TEXT)
LANGUAGE plpgsql
AS $$
DECLARE
    backup_filename TEXT;
    backup_command TEXT;
BEGIN
    -- Формирование имени файла резервной копии
    backup_filename := 'backup_' || to_char(current_timestamp, 'YYYYMMDD_HH24MISS') || '.sql';

    -- Формирование команды для создания резервной копии
    backup_command := format('pg_dump -U %s -h %s -d %s -F p -f %L', 
                             current_user, 
                             current_setting('server_version'),  -- Используем корректный параметр конфигурации
                             current_database(), 
                             destination_directory || '/' || backup_filename);

    -- Выполнение команды создания резервной копии
    PERFORM dblink_exec(backup_command);

    RAISE NOTICE 'Backup created successfully: %', destination_directory || '/' || backup_filename;
EXCEPTION
    WHEN others THEN
        RAISE EXCEPTION 'Error creating backup: %', SQLERRM;
END;
$$;


-- процедура для востановления базы с резервной копии 
CREATE OR REPLACE PROCEDURE restore_database_from_backup(backup_file TEXT)
LANGUAGE plpgsql
AS $$
BEGIN
    PERFORM pg_stat_file(backup_file);
    EXECUTE format('pg_restore -U %I -d %I -v %I', current_user, current_database(), backup_file);
    RAISE NOTICE 'Database restored successfully from backup file: %', backup_file;
END;
$$;


