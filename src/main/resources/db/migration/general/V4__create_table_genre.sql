CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE genre
(
    id         BIGSERIAL PRIMARY KEY ,
    uuid       UUID DEFAULT uuid_generate_v4() NOT NULL UNIQUE ,
    created_on TIMESTAMP(6) WITH TIME ZONE DEFAULT now(),
    name       VARCHAR(255)
);

ALTER TABLE song
    ADD genre_id BIGINT;

ALTER TABLE song
    ADD CONSTRAINT FK_SONG_ON_GENRE FOREIGN KEY (genre_id) REFERENCES genre (id);

INSERT INTO genre (name) VALUES ('default');