create table engine
(
    id SERIAL PRIMARY KEY,
    engineName VARCHAR NOT NULL
);

create table car
(
    id SERIAL PRIMARY KEY,
    carName VARCHAR NOT NULL,
    modelCar    VARCHAR   NOT NULL,
    bodyCar     VARCHAR   NOT NULL,
    photoCar    bytea,
    engineId INT NOT NULL REFERENCES engine (id)
);

create table driver
(
    id SERIAL PRIMARY KEY,
    driverName VARCHAR NOT NULL
);

create table history_owner
(
    id SERIAL PRIMARY KEY,
    driverId INT NOT NULL REFERENCES driver (id),
    carId INT NOT NULL REFERENCES car (id)
);