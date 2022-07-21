package ru.job4j.cars.model;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "car")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private byte[] photoCar;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nameId")
    private NameCar nameCar;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bodyId")
    private BodyCar bodyCar;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "engineId")
    private Engine engine;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "history_owner", joinColumns = {
            @JoinColumn(name = "driverId", nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "carId", nullable = false, updatable = false)})
    private List<Driver> drivers = new ArrayList<>();

    public Car() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

    public void addDriver(Driver driver) {
        drivers.add(driver);
    }

    public BodyCar getBodyCar() {
        return bodyCar;
    }

    public void setBodyCar(BodyCar bodyCar) {
        this.bodyCar = bodyCar;
    }

    public byte[] getPhotoCar() {
        return photoCar;
    }

    public void setPhotoCar(byte[] photoCar) {
        this.photoCar = photoCar;
    }

    public NameCar getNameCar() {
        return nameCar;
    }

    public void setNameCar(NameCar nameCar) {
        this.nameCar = nameCar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Car car = (Car) o;
        return id == car.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
