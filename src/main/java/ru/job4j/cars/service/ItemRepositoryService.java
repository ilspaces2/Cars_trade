package ru.job4j.cars.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Item;
import ru.job4j.cars.persistence.ItemRepository;

import java.util.List;

@ThreadSafe
@Service
public class ItemRepositoryService {

    private ItemRepository store;

    public ItemRepositoryService(ItemRepository store) {
        this.store = store;
    }

    public Item add(Item item) {
        return store.add(item);
    }

    public List<Item> findAll() {
        return store.findAll();
    }

    public List<Item> findWithPhoto() {
        return store.findWithPhoto();
    }

    public List<Item> findAddToday() {
        return store.findAddToday();
    }

    public List<Item> findByCarName(String carName) {
        return store.findByCarName(carName);
    }

    public List<Item> findItemsByUserId(int id) {
        return store.findItemsByUserId(id);
    }

    public Item findItemById(int id) {
        return store.findItemById(id);
    }

    public boolean sellItem(int id) {
        return store.sellItem(id);
    }

    public List<Item> findArchiveItems() {
        return store.findArchiveItems();
    }

    public boolean update(Item item) {
        return store.update(item);
    }
}
