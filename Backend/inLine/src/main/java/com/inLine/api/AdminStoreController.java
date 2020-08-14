package com.inLine.api;

import com.inLine.model.Hours;
import com.inLine.model.Store;
import com.inLine.service.AdminStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/admin/stores")
public class AdminStoreController {

    @Autowired
    private AdminStoreService adminStoreService;

    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createStore(@RequestBody Store store, @RequestHeader(name = "Authorization") String token) {
        return adminStoreService.addStore(store, token);
    }

    @DeleteMapping(path = "/{id}/delete")
    public ResponseEntity<?> deleteStoreById(@PathVariable("id") int id, @RequestHeader(name = "Authorization") String token) {
        return adminStoreService.deleteStoreById(id, token);
    }

    @PutMapping(path = "/{id}/update")
    public ResponseEntity<?> updateStoreById(@PathVariable("id") int storeId, @RequestBody Store update, @RequestHeader(name = "Authorization") String token) {
        return adminStoreService.updateStoreById(storeId, update, token);
    }

    @PostMapping(path = "/{id}/add-admin")
    public ResponseEntity<?> addAdminToStore(@PathVariable("id") int storeId, @RequestParam("email") String newAdminEmail, @RequestHeader (name = "Authorization") String token) {
        return adminStoreService.addAdminToStore(storeId, newAdminEmail, token);
    }

    @DeleteMapping(path = "/{id}/remove-admin")
    public ResponseEntity<?> removeAdminFromStore(@PathVariable("id") int storeId, @RequestParam("email") String removeAdminEmail, @RequestHeader (name = "Authorization") String token) {
        return adminStoreService.removeAdminFromStore(storeId, removeAdminEmail, token);
    }

    @PutMapping(path = "/{id}/update-hours")
    public ResponseEntity<?> updateStoreHoursByDay(@PathVariable("id") int storeId, @RequestBody Hours newHours, @RequestHeader(name = "Authorization") String token) {
        return adminStoreService.updateStoreHoursByDay(storeId, newHours, token);
    }

    @PutMapping(path = "/{id}/change-capacity/{cap}")
    public ResponseEntity<?> updateStoreCapacity(@PathVariable("id") int storeId, @PathVariable("cap") int cap, @RequestHeader(name = "Authorization") String token) {
        return adminStoreService.updateStoreCapacity(storeId, cap, token);
    }
}
