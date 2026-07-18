-- Test data for EMS application
-- Insert departments first (IDs chosen to match FK references below)
INSERT INTO department ( name, location, budget) VALUES ( 'Engineering', 'New York', 500000.00);
INSERT INTO department (name, location, budget) VALUES ( 'Research', 'Boston', 300000.00);

-- Insert employees
INSERT INTO employee ( name, email, phone_number, hire_date, salary, department_id) VALUES
  ( 'Alice Johnson', 'alice.johnson@example.com', '+12345678901', '2021-03-15', 90000.00, 1),
  ( 'Bob Smith',     'bob.smith@example.com',   '+19876543210', '2020-06-01', 80000.00, 1),
  ( 'Carol Lee',     'carol.lee@example.com',   '+11234567890', '2022-01-10', 75000.00, 2);

-- Insert projects
INSERT INTO project ( name, description, start_date, end_date, department_id) VALUES
  ( 'Project Phoenix', 'Next-gen platform', '2023-01-01', '2023-12-31', 1),
  ( 'Project Athena',  'AI research',       '2023-04-01', '2024-03-31', 2);

-- Insert project assignments (role stored as string enum)
INSERT INTO project_assigned ( role, employee_id, project_id) VALUES
  ( 'MANAGER',   1, 1),
  ( 'DEVELOPER', 2, 1),
  ( 'ANALYST',   3, 2);

-- Notes:
-- 1) Table/column names assume Hibernate's default physical naming strategy (camelCase -> snake_case)
-- 2) If your entities use different names or schemas, adjust the table/column identifiers accordingly
-- 3) This file will be executed by Spring Boot at startup when using the default DataSource initialization

