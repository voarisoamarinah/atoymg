--Default login
--Username: admin
--Password: admin

CREATE TABLE IF NOT EXISTS roles
(
  id SERIAL PRIMARY KEY,
  name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS users
(
  id SERIAL PRIMARY KEY,
  name VARCHAR(100) UNIQUE NOT NULL,
  email VARCHAR(100) UNIQUE NOT NULL CHECK (email ~ '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$'),
  password VARCHAR(100) NOT NULL,
  role_id INTEGER NOT NULL REFERENCES roles(id)
);

INSERT INTO roles (name) VALUES ('ROLE_ADMIN')
ON CONFLICT (name) DO NOTHING;

INSERT INTO users (name, email, password, role_id)
VALUES ('admin', 'admin@user.com', '$2a$10$GBvWdFZht2UZ8eOEXnLv.e/txUqjTUVnyotHTEtA.V9SAwVs6sgTS', 1)
ON CONFLICT (email) DO NOTHING;




