CREATE TABLE IF NOT EXISTS role(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(32) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS users(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS user_roles (
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    role_id UUID NOT NULL REFERENCES role(id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, role_id)
);

CREATE TABLE IF NOT EXISTS categories(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS carts(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    total_amount DECIMAL(19, 2) DEFAULT 0,
    user_id UUID UNIQUE REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS products(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL UNIQUE,
    brand VARCHAR(255) NOT NULL,
    price DECIMAL(19, 2)  NOT NULL DEFAULT 0,
    inventory INTEGER NOT NULL DEFAULT 0,
    description VARCHAR(255) NOT NULL UNIQUE,
    category_id UUID REFERENCES categories(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS images(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    file_name VARCHAR(255),
    file_type VARCHAR(100),
    image OID,
    download_url VARCHAR(500),
    product_id UUID NOT NULL REFERENCES products(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS carts(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    total_amount DECIMAL(19, 2) DEFAULT 0,
    user_id UUID UNIQUE REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS cart_items(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    quantity INTEGER NOT NULL DEFAULT 0,
    unit_price DECIMAL(19, 2),
    total_price DECIMAL(19, 2),
    product_id UUID NOT NULL REFERENCES products(id) ON DELETE CASCADE,
    cart_id UUID NOT NULL REFERENCES carts(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS orders(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    order_date DATE DEFAULT CURRENT_DATE,
    order_status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    order_total_amount DECIMAL(19, 2) DEFAULT 0,
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS order_items (
   id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
   price DECIMAL(19, 2) NOT NULL,
   quantity INTEGER NOT NULL DEFAULT 1,
   order_id UUID NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
   product_id UUID NOT NULL REFERENCES products(id) ON DELETE SET NULL,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);