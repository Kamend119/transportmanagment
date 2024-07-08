-- Представление для таблицы «Контактные лица»
CREATE VIEW contacts_view AS
SELECT
    id,
    lastname,
    firstname,
    patronymic,
    phone
FROM
    contacts;

-- Представление для таблицы «Автопарки»
CREATE VIEW autoparks_view AS
SELECT
    id,
    name,
    address,
    contact_id
FROM
    autoparks;

-- Представление для таблицы «Автомобили»
CREATE VIEW cars_view AS
SELECT
    id,
    licenseplate,
    model,
    brand,
    autopark_id
FROM
    cars;

-- Представление для таблицы «Дополнительные услуги»
CREATE VIEW additionalservices_view AS
SELECT
    id,
    name,
    cost,
    description
FROM
    additionalservices;

-- Представление для таблицы «Клиенты»
CREATE VIEW customers_view AS
SELECT
    id,
    lastname,
    firstname,
    patronymic,
    phone
FROM
    customers;

-- Представление для таблицы "Классификация грузов"
CREATE VIEW class_cargos_view AS
SELECT
    id,
    name,
    description
FROM
    class_cargos;

-- Представление для таблицы «Должности»
CREATE VIEW jobs_view AS
SELECT
    id,
    name
FROM
    jobs;

-- Представление для таблицы «Сотрудники»
CREATE VIEW employees_view AS
SELECT
    id,
    lastname,
    firstname,
    patronymic,
    dateofbirth,
    phone,
    login,
    job_id,
    passport_data,
    workdays
FROM
    employees;

-- Представление для таблицы «Точки назначения»
CREATE VIEW destinationpoints_view AS
SELECT
    id,
    type,
    city,
    address,
    arrivaldate,
    status,
    contract_id
FROM
    destinationpoints;

-- Представление для таблицы «Договоры»
CREATE VIEW contracts_view AS
SELECT
    id,
    conclusiondate,
    cost,
    customer_id,
    manager_id,
    driver_id,
    car_id,
    status
FROM
    contracts;

-- Представление для таблицы «Грузы»
CREATE VIEW cargos_view AS
SELECT
    id,
    name,
    weight,
    volume,
    contract_id,
    class_cargos_id
FROM
    cargos;

-- Представление для таблицы «ДоговорыДопУслуги»
CREATE VIEW contract_additionalservices_view AS
SELECT
    id,
    contractid,
    additionalserviceid
FROM
    contract_additionalservices;

-- Представление для таблицы «Аудит»
CREATE VIEW audit_log_view AS
SELECT
    audit_id,
    table_name,
    operation,
    changed_by,
    change_timestamp,
    old_data,
    new_data
FROM
    audit_log;
