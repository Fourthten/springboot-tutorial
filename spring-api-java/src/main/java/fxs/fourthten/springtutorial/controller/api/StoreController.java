package fxs.fourthten.springtutorial.controller.api;

import fxs.fourthten.springtutorial.domain.model.Store;
import fxs.fourthten.springtutorial.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController @RequestMapping("api/store") @RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;

    @GetMapping
    @PreAuthorize("hasAuthority('USER')")
    public Collection<Store> getStore() {
        return storeService.getAllProducts();
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public void addStore(@RequestBody Store store) {
        storeService.addStore(store);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public void removeStore(@PathVariable UUID id) {
        storeService.deleteStoreById(id);
    }
}
