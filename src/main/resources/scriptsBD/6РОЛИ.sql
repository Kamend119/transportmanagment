CREATE ROLE administrator_role;
GRANT CONNECT ON DATABASE transportmanagment TO administrator_role;
GRANT SELECT ON cargo_info TO administrator_role;
GRANT SELECT ON class_cargos_info TO administrator_role;
GRANT SELECT ON additional_services_info TO administrator_role;
GRANT SELECT ON contacts_info TO administrator_role;
GRANT SELECT ON autopark_info TO administrator_role;
GRANT SELECT ON car_info TO administrator_role;
GRANT SELECT ON jobs_info TO administrator_role;
GRANT SELECT ON employee_info TO administrator_role;
GRANT SELECT ON destination_points_info TO administrator_role;
GRANT SELECT ON customers_info TO administrator_role;
GRANT SELECT ON contract_info TO administrator_role;
GRANT SELECT ON audit_log_info TO administrator_role;

GRANT EXECUTE ON FUNCTION get_active_trip TO administrator_role;
GRANT EXECUTE ON FUNCTION generate_cargo_declaration TO administrator_role;
GRANT EXECUTE ON FUNCTION view_cargos_in_contract TO administrator_role;
GRANT EXECUTE ON FUNCTION view_active_contract_info TO administrator_role;
GRANT EXECUTE ON FUNCTION calculate_preliminary_cost TO administrator_role;
GRANT EXECUTE ON FUNCTION get_contract_info TO administrator_role;
GRANT EXECUTE ON FUNCTION contracts_summary_for_period TO administrator_role;
GRANT EXECUTE ON FUNCTION driver_performance_for_period TO administrator_role;
GRANT EXECUTE ON FUNCTION get_contract_destination_points TO administrator_role;
GRANT EXECUTE ON PROCEDURE create_database_backup TO administrator_role;
GRANT EXECUTE ON PROCEDURE restore_database_from_backup TO administrator_role;
GRANT EXECUTE ON PROCEDURE update_destinationpoint_status TO administrator_role;
GRANT EXECUTE ON PROCEDURE get_additional_services TO administrator_role;
GRANT EXECUTE ON PROCEDURE get_contracts_info TO administrator_role;


CREATE ROLE driver_role;
GRANT CONNECT ON DATABASE transportmanagment TO driver_role;
GRANT EXECUTE ON FUNCTION get_active_trip TO driver_role;
GRANT EXECUTE ON FUNCTION generate_cargo_declaration TO driver_role;
GRANT EXECUTE ON FUNCTION get_contract_destination_points TO driver_role;
GRANT EXECUTE ON FUNCTION view_cargos_in_contract TO driver_role;
GRANT EXECUTE ON PROCEDURE get_additional_services TO driver_role;
GRANT EXECUTE ON PROCEDURE get_contracts_info TO driver_role;


CREATE ROLE manager_role;
GRANT CONNECT ON DATABASE transportmanagment TO manager_role;
GRANT SELECT ON cargo_info TO manager_role;
GRANT SELECT ON class_cargos_info TO manager_role;
GRANT SELECT ON additional_services_info TO manager_role;
GRANT SELECT ON destination_points_info TO manager_role;
GRANT SELECT ON customers_info TO manager_role;
GRANT SELECT ON contract_info TO manager_role;

GRANT EXECUTE ON FUNCTION view_active_contract_info TO manager_role;
GRANT EXECUTE ON FUNCTION calculate_preliminary_cost TO manager_role;
GRANT EXECUTE ON FUNCTION get_contract_info TO manager_role;
GRANT EXECUTE ON PROCEDURE get_additional_services TO manager_role;


-- круд
GRANT EXECUTE ON PROCEDURE create_contact TO administrator_role;
GRANT EXECUTE ON FUNCTION get_contacts TO administrator_role;
GRANT EXECUTE ON PROCEDURE update_contact TO administrator_role;
GRANT EXECUTE ON FUNCTION delete_contact TO administrator_role;
GRANT EXECUTE ON PROCEDURE create_autopark TO administrator_role;
GRANT EXECUTE ON FUNCTION get_autoparks TO administrator_role;
GRANT EXECUTE ON PROCEDURE update_autopark TO administrator_role;
GRANT EXECUTE ON FUNCTION delete_autopark TO administrator_role;
GRANT EXECUTE ON PROCEDURE create_car TO administrator_role;
GRANT EXECUTE ON FUNCTION get_cars TO administrator_role;
GRANT EXECUTE ON PROCEDURE update_car TO administrator_role;
GRANT EXECUTE ON FUNCTION delete_car TO administrator_role;
GRANT EXECUTE ON PROCEDURE create_additional_service TO administrator_role;
GRANT EXECUTE ON FUNCTION get_additional_services TO administrator_role;
GRANT EXECUTE ON PROCEDURE update_additional_service TO administrator_role;
GRANT EXECUTE ON FUNCTION delete_additional_service TO administrator_role;
GRANT EXECUTE ON PROCEDURE create_job TO administrator_role;
GRANT EXECUTE ON FUNCTION get_jobs TO administrator_role;
GRANT EXECUTE ON PROCEDURE update_job TO administrator_role;
GRANT EXECUTE ON FUNCTION delete_job TO administrator_role;
GRANT EXECUTE ON PROCEDURE create_employee TO administrator_role;
GRANT EXECUTE ON FUNCTION get_employees TO administrator_role;
GRANT EXECUTE ON PROCEDURE update_employee TO administrator_role;
GRANT EXECUTE ON FUNCTION delete_employee TO administrator_role;


GRANT EXECUTE ON PROCEDURE create_destinationpoint TO administrator_role;
GRANT EXECUTE ON FUNCTION get_destinationpoints TO administrator_role;
GRANT EXECUTE ON PROCEDURE update_destinationpoint TO administrator_role;
GRANT EXECUTE ON FUNCTION delete_destinationpoint TO administrator_role;
GRANT EXECUTE ON PROCEDURE create_contract TO administrator_role;
GRANT EXECUTE ON FUNCTION get_contracts TO administrator_role;
GRANT EXECUTE ON PROCEDURE update_contract TO administrator_role;
GRANT EXECUTE ON FUNCTION delete_contract TO administrator_role;
GRANT EXECUTE ON PROCEDURE create_contract_additional_service TO administrator_role;
GRANT EXECUTE ON FUNCTION get_contract_additional_services TO administrator_role;
GRANT EXECUTE ON PROCEDURE update_contract_additional_service TO administrator_role;
GRANT EXECUTE ON FUNCTION delete_contract_additional_service TO administrator_role;
GRANT EXECUTE ON PROCEDURE create_customer TO administrator_role;
GRANT EXECUTE ON FUNCTION get_customers TO administrator_role;
GRANT EXECUTE ON PROCEDURE update_customer TO administrator_role;
GRANT EXECUTE ON FUNCTION delete_customer TO administrator_role;
GRANT EXECUTE ON PROCEDURE create_cargo_class TO administrator_role;
GRANT EXECUTE ON FUNCTION get_cargo_classes TO administrator_role;
GRANT EXECUTE ON PROCEDURE update_cargo_class TO administrator_role;
GRANT EXECUTE ON FUNCTION delete_cargo_class TO administrator_role;
GRANT EXECUTE ON PROCEDURE create_cargo TO administrator_role;
GRANT EXECUTE ON FUNCTION get_cargo TO administrator_role;
GRANT EXECUTE ON PROCEDURE update_cargo TO administrator_role;
GRANT EXECUTE ON FUNCTION delete_cargo TO administrator_role;


GRANT EXECUTE ON PROCEDURE create_destinationpoint TO manager_role;
GRANT EXECUTE ON FUNCTION get_destinationpoints TO manager_role;
GRANT EXECUTE ON PROCEDURE update_destinationpoint TO manager_role;
GRANT EXECUTE ON FUNCTION delete_destinationpoint TO manager_role;
GRANT EXECUTE ON PROCEDURE create_contract TO manager_role;
GRANT EXECUTE ON FUNCTION get_contracts TO manager_role;
GRANT EXECUTE ON PROCEDURE update_contract TO manager_role;
GRANT EXECUTE ON FUNCTION delete_contract TO manager_role;
GRANT EXECUTE ON PROCEDURE create_contract_additional_service TO manager_role;
GRANT EXECUTE ON FUNCTION get_contract_additional_services TO manager_role;
GRANT EXECUTE ON PROCEDURE update_contract_additional_service TO manager_role;
GRANT EXECUTE ON FUNCTION delete_contract_additional_service TO manager_role;
GRANT EXECUTE ON PROCEDURE create_customer TO manager_role;
GRANT EXECUTE ON FUNCTION get_customers TO manager_role;
GRANT EXECUTE ON PROCEDURE update_customer TO manager_role;
GRANT EXECUTE ON FUNCTION delete_customer TO manager_role;
GRANT EXECUTE ON PROCEDURE create_cargo_class TO manager_role;
GRANT EXECUTE ON FUNCTION get_cargo_classes TO manager_role;
GRANT EXECUTE ON PROCEDURE update_cargo_class TO manager_role;
GRANT EXECUTE ON FUNCTION delete_cargo_class TO manager_role;
GRANT EXECUTE ON PROCEDURE create_cargo TO manager_role;
GRANT EXECUTE ON FUNCTION get_cargo TO manager_role;
GRANT EXECUTE ON PROCEDURE update_cargo TO manager_role;
GRANT EXECUTE ON FUNCTION delete_cargo TO manager_role;

