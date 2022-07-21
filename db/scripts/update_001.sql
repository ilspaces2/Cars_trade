create table engine
(
    id         SERIAL PRIMARY KEY,
    engineName VARCHAR NOT NULL
);

create table bodyCar
(
    id       SERIAL PRIMARY KEY,
    bodyName VARCHAR NOT NULL
);

create table brandCar
(
    id        SERIAL PRIMARY KEY,
    brandName VARCHAR NOT NULL
);

create table nameCar
(
    id        SERIAL PRIMARY KEY,
    modelName VARCHAR NOT NULL,
    brandId   INT     NOT NULL REFERENCES brandCar (id)
);

create table car
(
    id       SERIAL PRIMARY KEY,
    photoCar bytea,
    engineId INT NOT NULL REFERENCES engine (id),
    bodyId   INT NOT NULL REFERENCES bodyCar (id),
    nameId   INT NOT NULL REFERENCES nameCar (id)
);

create table driver
(
    id         SERIAL PRIMARY KEY,
    driverName VARCHAR NOT NULL
);

create table history_owner
(
    id       SERIAL PRIMARY KEY,
    driverId INT NOT NULL REFERENCES driver (id),
    carId    INT NOT NULL REFERENCES car (id)
);