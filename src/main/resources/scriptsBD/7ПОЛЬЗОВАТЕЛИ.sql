CREATE USER administrator_user WITH PASSWORD 'admin1';
GRANT administrator_role TO administrator_user; 
CREATE USER driver_user WITH PASSWORD 'driver1';
GRANT driver_role TO driver_user;
CREATE USER manager_user WITH PASSWORD 'manager1';
GRANT manager_role TO manager_user;