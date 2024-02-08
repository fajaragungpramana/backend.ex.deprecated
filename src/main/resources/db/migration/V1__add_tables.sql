CREATE TABLE IF NOT EXISTS types(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(30) NOT NULL,
    category VARCHAR(30) NOT NULL,
    created_at BIGINT NOT NULL,
    updated_at BIGINT NULL,
    deleted_at BIGINT NULL
);

CREATE TABLE IF NOT EXISTS statuses(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(30) NOT NULL,
    category VARCHAR(30) NOT NULL,
    created_at BIGINT NOT NULL,
    updated_at BIGINT NULL,
    deleted_at BIGINT NULL
);

CREATE TABLE IF NOT EXISTS categories(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(30) NOT NULL,
    created_at BIGINT NOT NULL,
    updated_at BIGINT NULL,
    deleted_at BIGINT NULL
);

CREATE TABLE IF NOT EXISTS users(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    avatar VARCHAR(255) NULL,
    full_name VARCHAR(50) NOT NULL,
    email VARCHAR(60) NOT NULL,
    email_verified_at BIGINT NULL,
    password VARCHAR(255) NOT NULL,
    created_at BIGINT NOT NULL,
    updated_at BIGINT NULL,
    suspended_at BIGINT NULL,
    deleted_at BIGINT NULL
);

CREATE TABLE IF NOT EXISTS wallets(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    user_id BIGSERIAL NOT NULL,
    name VARCHAR(50) NOT NULL,
    balance BIGINT NOT NULL,
    type_id BIGSERIAL NOT NULL,
    created_at BIGINT NOT NULL,
    updated_at BIGINT NULL,
    deleted_at BIGINT NULL
);

CREATE TABLE IF NOT EXISTS transactions(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    user_id BIGSERIAL NOT NULL,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255) NULL,
    category_id BIGSERIAL NOT NULL,
    status_id BIGSERIAL NOT NULL,
    type_id BIGSERIAL NOT NULL,
    created_at BIGINT NOT NULL,
    updated_at BIGINT NULL,
    deleted_at BIGINT NULL
);

CREATE TABLE IF NOT EXISTS payments(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    transaction_id BIGSERIAL NOT NULL,
    amount BIGINT NOT NULL,
    status_id BIGSERIAL NOT NULL,
    wallet_id BIGSERIAL NOT NULL,
    created_at BIGINT NOT NULL,
    updated_at BIGINT NULL,
    deleted_at BIGINT NULL
);