create table users
(
    id       SERIAL PRIMARY KEY,
    userName VARCHAR NOT NULL,
    email    VARCHAR NOT NULL UNIQUE,
    pass     VARCHAR NOT NULL,
);
create table items
(
    id          SERIAL PRIMARY KEY,
    description VARCHAR   NOT NULL,
    modelCar    VARCHAR   NOT NULL,
    bodyCar     VARCHAR   NOT NULL,
    photoCar    bytea     NOT NULL,
    sale        boolean   NOT NULL,
    created     TIMESTAMP NOT NULL,
    userId      INT       NOT NULL REFERENCES users (id)
    carId       INT       NOT NULL REFERENCES car (id)
);
