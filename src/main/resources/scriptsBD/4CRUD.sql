-- Create (INSERT)
CREATE OR REPLACE PROCEDURE create_contact(
    IN p_lastname CHARACTER VARYING(40),
    IN p_firstname CHARACTER VARYING(40),
    IN p_patronymic CHARACTER VARYING(40),
    IN p_phone CHARACTER VARYING(11)
)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO contacts (lastname, firstname, patronymic, phone)
    VALUES (p_lastname, p_firstname, p_patronymic, p_phone);
END;
$$;

-- Read (SELECT)
CREATE OR REPLACE FUNCTION get_contact(p_id INT)
RETURNS TABLE (
    ids INT,
    lastnames CHARACTER VARYING(40),
    firstnames CHARACTER VARYING(40),
    patronymics CHARACTER VARYING(40),
    phones TEXT
)
LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY
    SELECT id, lastname, firstname, patronymic, phone
    FROM contacts
    WHERE id = p_id;
END;
$$;

-- Update (UPDATE)
CREATE OR REPLACE PROCEDURE update_contact(
    IN p_contact_id INT,
    IN p_lastname CHARACTER VARYING(40),
    IN p_firstname CHARACTER VARYING(40),
    IN p_patronymic CHARACTER VARYING(40),
    IN p_phone TEXT
)
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE contacts
    SET lastname = p_lastname,
        firstname = p_firstname,
        patronymic = p_patronymic,
        phone = p_phone
    WHERE id = p_contact_id;
END;
$$;

-- Delete (DELETE)
CREATE OR REPLACE FUNCTION delete_contact(contact_id INT) RETURNS VOID AS $$
BEGIN
    DELETE FROM contacts WHERE id = contact_id;
END;
$$ LANGUAGE plpgsql;


-- Create (INSERT)
CREATE OR REPLACE PROCEDURE create_autopark(
    IN p_name CHARACTER VARYING(100),
    IN p_address CHARACTER VARYING(255),
    IN p_contact_id INT
)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO autoparks (name, address, contact_id)
    VALUES (p_name, p_address, p_contact_id);
END;
$$;

-- Read (SELECT)
CREATE OR REPLACE FUNCTION get_autopark(p_id INT)
RETURNS TABLE (
    ids INT,
    names CHARACTER VARYING(100),
    addresss CHARACTER VARYING(255),
    contact_ids BIGINT
)
LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY
    SELECT id, name, address, contact_id
    FROM autoparks
    WHERE id = p_id;
END;
$$;

-- Update (UPDATE)
CREATE OR REPLACE PROCEDURE update_autopark(
    IN p_autopark_id INT,
    IN p_name CHARACTER VARYING(100),
    IN p_address CHARACTER VARYING(255),
    IN p_contact_id INT
)
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE autoparks
    SET name = p_name,
        address = p_address,
        contact_id = p_contact_id
    WHERE id = p_autopark_id;
END;
$$;

-- Delete (DELETE)
CREATE OR REPLACE FUNCTION delete_autopark(autopark_id INT) RETURNS VOID AS $$
BEGIN
    DELETE FROM autoparks WHERE id = autopark_id;
END;
$$ LANGUAGE plpgsql;


-- Create (INSERT)
CREATE OR REPLACE PROCEDURE create_car(
    IN p_licenseplate CHARACTER VARYING(10),
    IN p_model CHARACTER VARYING(100),
    IN p_brand CHARACTER VARYING(100),
    IN p_autopark_id INT
)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO cars (licenseplate, model, brand, autopark_id)
    VALUES (p_licenseplate, p_model, p_brand, p_autopark_id);
END;
$$;

-- Read (SELECT)
CREATE OR REPLACE FUNCTION get_car(p_id INT)
RETURNS TABLE (
    ids INT,
    licenseplates CHARACTER VARYING(10),
    models CHARACTER VARYING(100),
    brands CHARACTER VARYING(100),
    autopark_ids BIGINT
)
LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY
    SELECT id, licenseplate, model, brand, autopark_id
    FROM cars
    WHERE id = p_id;
END;
$$;

-- Update (UPDATE)
CREATE OR REPLACE PROCEDURE update_car(
    IN p_car_id INT,
    IN p_licenseplate CHARACTER VARYING(10),
    IN p_model CHARACTER VARYING(100) ,
    IN p_brand CHARACTER VARYING(100) ,
    IN p_autopark_id INT
)
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE cars
    SET licenseplate = p_licenseplate,
        model = p_model,
        brand = p_brand,
        autopark_id = p_autopark_id
    WHERE id = p_car_id;
END;
$$;

-- Delete (DELETE)
CREATE OR REPLACE FUNCTION delete_car(car_id INT) RETURNS VOID AS $$
BEGIN
    DELETE FROM cars WHERE id = car_id;
END;
$$ LANGUAGE plpgsql;


-- Create (INSERT)
CREATE OR REPLACE PROCEDURE create_additional_service(
    IN p_name CHARACTER VARYING(100) ,
    IN p_cost NUMERIC(10, 2) ,
    IN p_description TEXT
)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO additionalservices (name, cost, description)
    VALUES (p_name, p_cost, p_description);
END;
$$;

-- Read (SELECT)
CREATE OR REPLACE FUNCTION get_additional_service(p_id INT)
RETURNS TABLE (
    ids INT,
    names CHARACTER VARYING(100),
    costs NUMERIC(10, 2),
    descriptions TEXT
)
LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY
    SELECT id, name, cost, description
    FROM additionalservices
    WHERE id = p_id;
END;
$$;

-- Update (UPDATE)
CREATE OR REPLACE PROCEDURE update_additional_service(
    IN p_service_id INT,
    IN p_name CHARACTER VARYING(100) ,
    IN p_cost NUMERIC(10, 2) ,
    IN p_description CHARACTER VARYING
)
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE additionalservices
    SET name = p_name,
        cost = p_cost,
        description = p_description
    WHERE id = p_service_id;
END;
$$;

-- Delete (DELETE)
CREATE OR REPLACE FUNCTION delete_additional_service(service_id INT) RETURNS VOID AS $$
BEGIN
    DELETE FROM additionalservices WHERE id = service_id;
END;
$$ LANGUAGE plpgsql;


-- Create (INSERT)
CREATE OR REPLACE PROCEDURE create_customer(
    IN p_lastname CHARACTER VARYING(40) ,
    IN p_firstname CHARACTER VARYING(40) ,
    IN p_patronymic CHARACTER VARYING(40),
    IN p_phone TEXT
)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO customers (lastname, firstname, patronymic, phone)
    VALUES (p_lastname, p_firstname, p_patronymic, p_phone);
END;
$$;

-- Read (SELECT)
CREATE OR REPLACE FUNCTION get_customer(p_id INT)
RETURNS TABLE (
    ids INT,
    lastnames CHARACTER VARYING(40),
    firstnames CHARACTER VARYING(40),
    patronymics CHARACTER VARYING(40),
    phones TEXT
)
LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY
    SELECT id, lastname, firstname, patronymic, phone
    FROM customers
    WHERE id = p_id;
END;
$$;

-- Update (UPDATE)
CREATE OR REPLACE PROCEDURE update_customer(
    IN p_customer_id INT,
    IN p_lastname CHARACTER VARYING(40) ,
    IN p_firstname CHARACTER VARYING(40) ,
    IN p_patronymic CHARACTER VARYING(40),
    IN p_phone TEXT
)
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE customers
    SET lastname = p_lastname,
        firstname = p_firstname,
        patronymic = p_patronymic,
        phone = p_phone
    WHERE id = p_customer_id;
END;
$$;

-- Delete (DELETE)
CREATE OR REPLACE FUNCTION delete_customer(customer_id INT) RETURNS VOID AS $$
BEGIN
    DELETE FROM customers WHERE id = customer_id;
END;
$$ LANGUAGE plpgsql;


-- Create (INSERT)
CREATE OR REPLACE PROCEDURE create_cargo_class(
    IN p_name CHARACTER VARYING(255),
    IN p_description TEXT
)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO class_cargos (name, description)
    VALUES (p_name, p_description);
END;
$$;

-- Read (SELECT)
CREATE OR REPLACE FUNCTION get_cargo_class(p_id INT)
RETURNS TABLE (
    ids INT,
    names CHARACTER VARYING(255),
    descriptions TEXT
)
LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY
    SELECT id, name, description
    FROM class_cargos
    WHERE id = p_id;
END;
$$;

-- Update (UPDATE)
CREATE OR REPLACE PROCEDURE update_cargo_class(
    IN p_cargo_class_id INT,
    IN p_name CHARACTER VARYING(255),
    IN p_description TEXT
)
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE class_cargos
    SET name = p_name,
        description = p_description
    WHERE id = p_cargo_class_id;
END;
$$;

-- Delete (DELETE)
CREATE OR REPLACE FUNCTION delete_cargo_class(cargo_class_id INT) RETURNS VOID AS $$
BEGIN
    DELETE FROM class_cargos WHERE id = cargo_class_id;
END;
$$ LANGUAGE plpgsql;


-- Create (INSERT)
CREATE OR REPLACE PROCEDURE create_cargo(
    IN p_name CHARACTER VARYING(255),
    IN p_weight NUMERIC(10, 2),
    IN p_volume NUMERIC(10, 2),
    IN p_contract_id INT,
    IN p_class_cargos_id INT
)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO cargos (name, weight, volume, contract_id, class_cargos_id)
    VALUES (p_name, p_weight, p_volume, p_contract_id, p_class_cargos_id);
END;
$$;

-- Read (SELECT)
CREATE OR REPLACE FUNCTION get_cargo(p_id INT)
RETURNS TABLE (
    ids INT,
    names CHARACTER VARYING(255),
    weights NUMERIC(10, 2),
    volumes NUMERIC(10, 2),
    contract_ids BIGINT,
    class_cargos_ids BIGINT
)
LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY
    SELECT id, name, weight, volume, contract_id, class_cargos_id
    FROM cargos
    WHERE id = p_id;
END;
$$;

-- Update (UPDATE)
CREATE OR REPLACE PROCEDURE update_cargo(
    IN p_cargo_id INT,
    IN p_name CHARACTER VARYING(255),
    IN p_weight NUMERIC(10, 2),
    IN p_volume NUMERIC(10, 2),
    IN p_contract_id INT,
    IN p_class_cargos_id INT
)
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE cargos
    SET name = p_name,
        weight = p_weight,
        volume = p_volume,
        contract_id = p_contract_id,
        class_cargos_id = p_class_cargos_id
    WHERE id = p_cargo_id;
END;
$$;

-- Delete (DELETE)
CREATE OR REPLACE FUNCTION delete_cargo(cargo_id INT) RETURNS VOID AS $$
BEGIN
    DELETE FROM cargos WHERE id = cargo_id;
END;
$$ LANGUAGE plpgsql;


-- Create (INSERT)
CREATE OR REPLACE PROCEDURE create_job(
    IN p_name CHARACTER VARYING(100) 
)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO jobs (name)
    VALUES (p_name);
END;
$$;

-- Read (SELECT)
CREATE OR REPLACE FUNCTION get_job(p_id INT)
RETURNS TABLE (
    ids INT,
    names CHARACTER VARYING(100)
)
LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY
    SELECT id, name
    FROM jobs
    WHERE id = p_id;
END;
$$;

-- Update (UPDATE)
CREATE OR REPLACE PROCEDURE update_job(
    IN p_job_id INT,
    IN p_name CHARACTER VARYING(100) 
)
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE jobs
    SET name = p_name
    WHERE id = p_job_id;
END;
$$;

-- Delete (DELETE)
CREATE OR REPLACE FUNCTION delete_job(job_id INT) RETURNS VOID AS $$
BEGIN
    DELETE FROM jobs WHERE id = job_id;
END;
$$ LANGUAGE plpgsql;


-- Create (INSERT)
CREATE OR REPLACE PROCEDURE create_employee(
    IN p_lastname CHARACTER VARYING(40) ,
    IN p_firstname CHARACTER VARYING(40) ,
    IN p_patronymic CHARACTER VARYING(40),
    IN p_dateofbirth DATE ,
    IN p_phone TEXT ,
    IN p_passport_data JSON,
    IN p_workdays TEXT[],
    IN p_login CHARACTER VARYING(100) ,
    IN p_password CHARACTER VARYING(100) ,
    IN p_job_id INT
)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO employees (lastname, firstname, patronymic, dateofbirth, phone, passport_data, workdays, login, password, job_id)
    VALUES (p_lastname, p_firstname, p_patronymic, p_dateofbirth, p_phone, p_passport_data, p_workdays, p_login, p_password, p_job_id);
END;
$$;

-- Read (SELECT)
CREATE OR REPLACE FUNCTION get_employee(p_id BIGINT)
RETURNS TABLE (
    ids INT,
    lastnames CHARACTER VARYING(40),
    firstnames CHARACTER VARYING(40),
    patronymics CHARACTER VARYING(40),
    dateofbirths DATE,
    phones TEXT,
    passport_datas JSON,
    workdayss TEXT[],
    logins CHARACTER VARYING(100),
    job_ids BIGINT
)
LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY
    SELECT id, lastname, firstname, patronymic, dateofbirth, phone, passport_data, workdays, login, job_id
    FROM employees
    WHERE id = p_id;
END;
$$;

-- Update (UPDATE)
CREATE OR REPLACE PROCEDURE update_employee(
    IN p_employee_id INT,
    IN p_lastname CHARACTER VARYING(40) ,
    IN p_firstname CHARACTER VARYING(40) ,
    IN p_patronymic CHARACTER VARYING(40),
    IN p_dateofbirth DATE ,
    IN p_phone TEXT ,
    IN p_passport_data JSON,
    IN p_workdays TEXT[],
    IN p_login CHARACTER VARYING(100) ,
    IN p_password CHARACTER VARYING(100) ,
    IN p_job_id INT
)
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE employees
    SET lastname = p_lastname,
        firstname = p_firstname,
        patronymic = p_patronymic,
        dateofbirth = p_dateofbirth,
        phone = p_phone,
        passport_data = p_passport_data,
        workdays = p_workdays,
        login = p_login,
        password = p_password,
        job_id = p_job_id
    WHERE id = p_employee_id;
END;
$$;

-- Delete (DELETE)
CREATE OR REPLACE FUNCTION delete_employee(employee_id INT) RETURNS VOID AS $$
BEGIN
    DELETE FROM employees WHERE id = employee_id;
END;
$$ LANGUAGE plpgsql;


-- Create (INSERT)
CREATE OR REPLACE PROCEDURE create_destinationpoint(
    IN p_type type_destinationpoints,
    IN p_city CHARACTER VARYING(100) ,
    IN p_address CHARACTER VARYING(255) ,
    IN p_arrivaldate DATE ,
    IN p_status status_destination,
    in contract_ids INT
)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO destinationpoints (contract_id, type, city, address, arrivaldate, status)
    VALUES (contract_ids, p_type, p_city, p_address, p_arrivaldate, p_status);
END;
$$;

-- Read (SELECT)
CREATE OR REPLACE FUNCTION get_destinationpoint(p_id INT)
RETURNS TABLE (
    ids INT,
    types type_destinationpoints,
    citys CHARACTER VARYING(100),
    addresss CHARACTER VARYING(255),
    arrivaldates DATE,
    statuss status_destination,
    contract_ids BIGINT
)
LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY
    SELECT id, type, city, address, arrivaldate, status, contract_id
    FROM destinationpoints
    WHERE id = p_id;
END;
$$;

-- Update (UPDATE)
CREATE OR REPLACE PROCEDURE update_destinationpoint(
    IN p_destinationpoint_id INT,
    IN p_type type_destinationpoints,
    IN p_city CHARACTER VARYING(100) ,
    IN p_address CHARACTER VARYING(255) ,
    IN p_arrivaldate DATE ,
    IN p_status status_destination,
    in contract_ids INT
)
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE destinationpoints
    SET type = p_type,
        city = p_city,
        address = p_address,
        arrivaldate = p_arrivaldate,
        status = p_status,
        contract_id = contract_ids
    WHERE id = p_destinationpoint_id;
END;
$$;

-- Delete (DELETE)
CREATE OR REPLACE FUNCTION delete_destinationpoint(destinationpoint_id INT) RETURNS VOID AS $$
BEGIN
    DELETE FROM destinationpoints WHERE id = destinationpoint_id;
END;
$$ LANGUAGE plpgsql;


-- Create (INSERT)
CREATE OR REPLACE PROCEDURE create_contract(
    IN p_customer_id INT,
    IN p_manager_id INT,
    IN p_driver_id INT,
    IN p_car_id INT
)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO contracts (customer_id, manager_id, driver_id, car_id, status)
    VALUES (p_customer_id, p_manager_id, p_driver_id, p_car_id, 'В работе');
END;
$$;

-- Read (SELECT)
CREATE OR REPLACE FUNCTION get_contract(p_id INT)
RETURNS TABLE (
    ids INT,
    conclusiondates DATE,
    costs NUMERIC(10, 2),
    customer_ids BIGINT,
    manager_ids BIGINT,
    driver_ids BIGINT,
    car_ids BIGINT
)
LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY
    SELECT id, conclusiondate, cost, customer_id, manager_id, driver_id, car_id
    FROM contracts
    WHERE id = p_id;
END;
$$;

-- Update (UPDATE)
CREATE OR REPLACE PROCEDURE update_contract(
    IN p_contract_id INT,
    IN p_conclusiondate DATE,
    IN p_customer_id INT,
    IN p_manager_id INT,
    IN p_driver_id INT,
    IN p_car_id INT
)
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE contracts
    SET conclusiondate = p_conclusiondate,
        customer_id = p_customer_id,
        manager_id = p_manager_id,
        driver_id = p_driver_id,
        car_id = p_car_id
    WHERE id = p_contract_id;
END;
$$;

-- Delete (DELETE)
CREATE OR REPLACE FUNCTION delete_contract(contract_id INT) RETURNS VOID AS $$
BEGIN
    DELETE FROM contracts WHERE id = contract_id;
END;
$$ LANGUAGE plpgsql;


-- Create (INSERT)
CREATE OR REPLACE PROCEDURE create_contract_additional_service(
    IN p_contract_id INT,
    IN p_additionalservice_id INT
)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO contract_additionalservices (contractid, additionalserviceid)
    VALUES (p_contract_id, p_additionalservice_id);
END;
$$;

-- Read (SELECT)
CREATE OR REPLACE FUNCTION get_contract_additional_service(p_id INT)
RETURNS TABLE (
    ids INT,
    contractids BIGINT,
    additionalserviceids BIGINT
)
LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY
    SELECT id, contractid, additionalserviceid
    FROM contract_additionalservices
    WHERE id = p_id;
END;
$$;

-- Update (UPDATE)
CREATE OR REPLACE PROCEDURE update_contract_additional_service(
    IN p_id INT,
    IN p_contract_id INT,
    IN p_additionalservice_id INT
)
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE contract_additionalservices
    SET contractid = p_contract_id,
        additionalserviceid = p_additionalservice_id
    WHERE id = p_id;
END;
$$;


-- Delete (DELETE)
CREATE OR REPLACE FUNCTION delete_contract_additional_service(contract_additionalservice_id INT) RETURNS VOID AS $$
BEGIN
    DELETE FROM contract_additionalservices WHERE id = contract_additionalservice_id;
END;
$$ LANGUAGE plpgsql;


