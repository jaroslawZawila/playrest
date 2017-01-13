# Arrangements

# --- !Ups
CREATE TABLE arrangements
(
    id INTEGER SERIAL PRIMARY KEY NOT NULL ,
    paymentday INTEGER,
    status VARCHAR(50)
);
CREATE UNIQUE INDEX arrangements_id_uindex ON arrangements (id);

# --- !Downs

DROP TABLE arrangements;