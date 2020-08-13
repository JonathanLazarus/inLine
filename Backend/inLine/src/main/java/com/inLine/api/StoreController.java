package com.inLine.api;

import com.inLine.SecurityAndJWT.jwt.JwtTokenUtil;
import com.inLine.dao.AccountDao;
import com.inLine.dao.StoreDao;
import com.inLine.model.Account;
import com.inLine.model.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/stores")
public class StoreController {

    @Autowired
    private StoreDao storeDao;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private JwtTokenUtil jwtUtil;

    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void createStore(@RequestBody Store store, @RequestHeader (name = "Authorization") String token) {
        String email = jwtUtil.getUsernameFromToken(token.substring(7));
        Account admin = accountDao.selectUserByEmail(email)
                .orElse(null);
        store.setManagers(Collections.singletonList(admin));
        storeDao.save(store);
    }

    @GetMapping
    public List<Store> getAllStores() {
        return  storeDao.findAll();
    }

    @GetMapping(path = "/{id}")
    public Store getStoreById(@PathVariable("id") int id) {
        return storeDao.findById(id).
                orElse(null);
    }

    @PutMapping(path = "/{id}/update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateStoreById(@PathVariable("id") int id, @RequestBody Store update) {
        Store s = storeDao.findById(id).orElse(null);
        if (s == null) return;
        update.setId(id);
        storeDao.save(update);
    }

    @GetMapping(path = "/search-prefix")
    public List<Store> getStoreWithPrefix(@RequestParam String prefix) {
        return storeDao.findStoresByPrefix(prefix);
    }

    @DeleteMapping(path = "/{id}/delete")
    public void deleteStoreById(@PathVariable("id") int id) {
        storeDao.deleteById(id);
    }


}
