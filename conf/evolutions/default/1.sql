# Arrangements

# --- !Ups

CREATE TABLE arrangements
(
    id INTEGER PRIMARY KEY NOT NULL,
    paymentday INTEGER,
    status VARCHAR(50)
);
CREATE UNIQUE INDEX arrangements_id_uindex ON arrangements (id);

INSERT INTO arrangements (id, paymentday, status) VALUES (1, 15, 'ACTIVE')

# --- !Downs

DROP TABLE arrangements;