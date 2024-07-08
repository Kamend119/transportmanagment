-- Создание типа «СтатусДоговора»
CREATE TYPE status_agreement AS ENUM ('Выполнен', 'В работе', 'Отменен');

-- Создание типа «СтатусТочки»
CREATE TYPE status_destination AS ENUM ('Доставлен', 'В работе', 'Отменен');

-- Создание типа «ТипТочкиНазначения»
CREATE TYPE type_destinationpoints AS ENUM ('Отправление', 'Прибытие');

-- Создание таблицы «Контактные лица»
CREATE TABLE contacts (
    id SERIAL PRIMARY KEY,
    lastname CHARACTER VARYING(40) NOT NULL,
    firstname CHARACTER VARYING(40) NOT NULL,
    patronymic CHARACTER VARYING(40),
    phone TEXT NOT NULL,
    CONSTRAINT check_phone_contact CHECK(phone ~ '^\d{11}$'),
    CONSTRAINT unique_phone_contact UNIQUE(phone)
);

-- Создание таблицы «Автопарки»
CREATE TABLE autoparks (
    id SERIAL PRIMARY KEY,
    name CHARACTER VARYING (100) NOT NULL,
    address CHARACTER VARYING (255) NOT NULL,
    contact_id BIGINT,
    FOREIGN KEY (contact_id) REFERENCES contacts(id)
);

-- Создание таблицы «Автомобили»
CREATE TABLE cars (
    id SERIAL PRIMARY KEY,
    licenseplate CHARACTER VARYING(10) NOT NULL,
    model CHARACTER VARYING(100) NOT NULL,
    brand CHARACTER VARYING(100) NOT NULL,
    autopark_id BIGINT,
    CONSTRAINT unique_licenseplate_cars UNIQUE(licenseplate),
    FOREIGN KEY (autopark_id) REFERENCES autoparks(id)
);

-- Создание таблицы «Дополнительные услуги»
CREATE TABLE additionalservices (
    id SERIAL PRIMARY KEY,
    name CHARACTER VARYING(100) NOT NULL,
    cost  NUMERIC(10, 2) NOT NULL,
    description TEXT,
    CONSTRAINT check_cost_additionalservices CHECK(cost > 0)
);

-- Создание таблицы «Клиенты»
CREATE TABLE customers (
    id SERIAL PRIMARY KEY,
    lastname CHARACTER VARYING(40) NOT NULL,
    firstname CHARACTER VARYING(40) NOT NULL,
    patronymic CHARACTER VARYING(40),
    phone TEXT NOT NULL,
    CONSTRAINT check_phone_customers CHECK(phone ~ '^\d{11}$'),
    CONSTRAINT unique_phone_customers UNIQUE(phone)
);

-- Создание таблицы "Классификация грузов"
CREATE TABLE class_cargos (
    id SERIAL PRIMARY KEY,
    name CHARACTER VARYING(255),
    description TEXT
);

-- Создание таблицы «Должности»
CREATE TABLE jobs (
    id SERIAL PRIMARY KEY,
    name CHARACTER VARYING (100) NOT NULL
);

-- Создание таблицы «Сотрудники»
CREATE TABLE employees (
    id SERIAL PRIMARY KEY,
    lastname CHARACTER VARYING(40) NOT NULL,
    firstname CHARACTER VARYING(40) NOT NULL,
    patronymic CHARACTER VARYING(40),
    dateofbirth DATE NOT NULL,
    phone TEXT NOT NULL,
    passport_data JSON,
    workdays TEXT[],
    login CHARACTER VARYING(100) NOT NULL,
    password CHARACTER VARYING(100) NOT NULL,
    job_id BIGINT NOT NULL,
    CONSTRAINT check_phone_employees CHECK(phone ~ '^\d{11}$'),
    CONSTRAINT unique_phone_employees UNIQUE(phone),
    CONSTRAINT unique_login_employees UNIQUE(login),
    FOREIGN KEY (job_id) REFERENCES jobs(id)
);

CREATE TABLE contracts (
    id SERIAL PRIMARY KEY,
    conclusiondate DATE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    cost NUMERIC(10, 2) DEFAULT 0.0 NOT NULL, 
    customer_id BIGINT NOT NULL,
    manager_id BIGINT NOT NULL,
    driver_id BIGINT NOT NULL,
    car_id BIGINT NOT NULL,
    status status_agreement,
    FOREIGN KEY (customer_id) REFERENCES customers(id),
    FOREIGN KEY (manager_id) REFERENCES employees(id),
    FOREIGN KEY (driver_id) REFERENCES employees(id),
    FOREIGN KEY (car_id) REFERENCES cars(id)
);

-- Создание таблицы «Точки назначения»
CREATE TABLE destinationpoints (
    id SERIAL PRIMARY KEY,
    contract_id BIGINT NOT NULL,
    type type_destinationpoints NOT NULL,
    city CHARACTER VARYING(100) NOT NULL,
    address CHARACTER VARYING(255) NOT NULL,
    arrivaldate DATE NOT NULL,
    status status_destination,
    CONSTRAINT check_arrivaldate_destinationpoints CHECK(arrivaldate >= CURRENT_DATE),
    FOREIGN KEY (contract_id) REFERENCES contracts(id)
);

-- Создание таблицы «Грузы»
CREATE TABLE cargos (
    id SERIAL PRIMARY KEY,
    name CHARACTER VARYING(255),
    weight NUMERIC(10, 2),
    volume NUMERIC(10, 2),
    contract_id BIGINT NOT NULL,
    class_cargos_id BIGINT NOT NULL,
    CONSTRAINT check_weight_cargos CHECK(weight > 0),
    CONSTRAINT check_volume_cargos CHECK(volume > 0),
    FOREIGN KEY (class_cargos_id) REFERENCES class_cargos(id),
    FOREIGN KEY (contract_id) REFERENCES contracts(id)
);

-- Создание таблицы «ДоговорыДопУслуги»
CREATE TABLE contract_additionalservices (
    id SERIAL PRIMARY KEY,
    contractid BIGINT,
    additionalserviceid BIGINT,
    FOREIGN KEY (contractid) REFERENCES contracts(id),
    FOREIGN KEY (additionalserviceid) REFERENCES additionalservices(id)
);

-- Создание таблицы «Аудит»
CREATE TABLE audit_log (
    audit_id SERIAL PRIMARY KEY,
    table_name TEXT NOT NULL,
    operation CHARACTER VARYING(100) NOT NULL,
    changed_by TEXT NOT NULL,
    change_timestamp TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    old_data JSON,
    new_data JSON
);

CREATE INDEX idx_employees_login ON employees(login);
CREATE INDEX idx_contracts_customer_id ON contracts(customer_id);
CREATE INDEX idx_contracts_manager_id ON contracts(manager_id);
CREATE INDEX idx_contracts_driver_id ON contracts(driver_id);
CREATE INDEX idx_contracts_car_id ON contracts(car_id);
CREATE INDEX idx_contracts_conclusiondate ON contracts(conclusiondate);