package com.inLine.api;

import com.inLine.model.Store;
import com.inLine.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping(value = "/stores")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @GetMapping(path = "/{id}")
    public Store getStoreById(@PathVariable(name = "id") @Positive int id) {
        return storeService.getStoreById(id);
    }

    @GetMapping(path = "/search-prefix")
    public List<Store> getStoresWithPrefix(@RequestParam(name = "prefix") @NotBlank String prefix) {
        return storeService.getStoresWithPrefix(prefix);
    }
}
