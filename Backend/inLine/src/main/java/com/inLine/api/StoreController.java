package com.inLine.api;

import com.inLine.dao.LocationDao;
import com.inLine.dao.StoreDao;
import com.inLine.model.Store;
import com.inLine.model.containter.StoreLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/stores")
public class StoreController {

    @Autowired
    private StoreDao storeDao;

    @Autowired
    private LocationDao locationDao;

    @PostMapping
    public void createStore(@RequestBody StoreLocation containter){
        int locationId = locationDao.save(containter.getLocation()).getId();
        Store s = containter.getStore();
        s.setLocationId(locationId);
        storeDao.save(s);
    }

    @GetMapping
    public List<Store> getAllStores() {
        return  storeDao.findAll();
    }

    @GetMapping(path = "{id}")
    public Store getStoreById(@PathVariable("id") int id) {
        return storeDao.findById(id).
                orElse(null);
    }

    @PutMapping(path = "{id}")
    public void updateStoreById(@PathVariable("id") int id, @RequestBody Store update) {
        Store s = storeDao.findById(id).orElse(null);
        if (s == null) return;
        update.setId(id);
        storeDao.save(update);
    }
}
