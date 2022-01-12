package fxs.fourthten.springtutorial.service;

import fxs.fourthten.springtutorial.domain.model.Store;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class StoreService {

    private final Map<UUID, Store> storeMap = new ConcurrentHashMap<>();
    private final AtomicInteger idCounter = new AtomicInteger(0);

    public Collection<Store> getAllProducts() {
        return storeMap.values();
    }

    public Store getProductById(UUID id) {
        return storeMap.get(id);
    }

    public void addStore(Store store) {
        if (storeMap.values().stream().anyMatch(p -> p.getName().equals(store.getName()))) {
            throw new IllegalArgumentException(String.format("Store with name %s already exists", store.getName()));
        }

        storeMap.put(store.getId(), store);
    }

    public void deleteStoreById(UUID id) {
        if (!storeMap.containsKey(id)) {
            throw new IllegalArgumentException(String.format("Product with id %d doesn't exist", id));
        }

        storeMap.remove(id);
    }

}
