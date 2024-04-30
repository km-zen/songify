CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE album
(
    id           BIGSERIAL PRIMARY KEY ,
    uuid         UUID DEFAULT uuid_generate_v4() NOT NULL UNIQUE ,
    created_on   TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
    title        VARCHAR(255),
    release_date TIMESTAMP(6) WITH TIME ZONE DEFAULT now()
);