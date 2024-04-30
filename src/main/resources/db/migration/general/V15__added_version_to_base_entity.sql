ALTER TABLE album
    ADD version BIGINT default 0;

ALTER TABLE artist
    ADD version BIGINT default 0;

ALTER TABLE genre
    ADD version BIGINT default 0;

ALTER TABLE song
    ADD version BIGINT default 0;