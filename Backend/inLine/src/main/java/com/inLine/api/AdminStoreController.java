package com.inLine.api;

import com.inLine.model.Hours;
import com.inLine.model.Store;
import com.inLine.service.AdminStoreService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping(value = "/admin/stores")
public class AdminStoreController {

    @Autowired
    private AdminStoreService adminStoreService;

    @Operation(summary = "Create store", description = "Adds a store to the database")
    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createStore(@RequestBody @Valid Store store, @RequestHeader(name = "Authorization") String token) {
        return adminStoreService.addStore(store, token);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteStoreById(@PathVariable(name = "id") @Positive int id, @RequestHeader(name = "Authorization") String token) {
        return adminStoreService.deleteStoreById(id, token);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateStoreById(@PathVariable(name = "id") @Positive int storeId, @RequestBody @Valid Store update, @RequestHeader(name = "Authorization") String token) {
        return adminStoreService.updateStoreById(storeId, update, token);
    }

    @PostMapping(path = "/{id}/admin")
    public ResponseEntity<?> addAdminToStore(@PathVariable(name = "id") @Positive int storeId, @RequestParam("email") @Email String newAdminEmail, @RequestHeader (name = "Authorization") String token) {
        return adminStoreService.addAdminToStore(storeId, newAdminEmail, token);
    }

    @DeleteMapping(path = "/{id}/admin")
    public ResponseEntity<?> removeAdminFromStore(@PathVariable(name = "id") @Positive int storeId, @RequestParam("email") @Email String removeAdminEmail, @RequestHeader (name = "Authorization") String token) {
        return adminStoreService.removeAdminFromStore(storeId, removeAdminEmail, token);
    }

    @PutMapping(path = "/{id}/hours")
    public ResponseEntity<?> updateStoreHoursByDay(@PathVariable(name = "id") @Positive int storeId, @RequestBody @Valid Hours newHours, @RequestHeader(name = "Authorization") String token) {
        return adminStoreService.updateStoreHoursByDay(storeId, newHours, token);
    }

    @PutMapping(path = "/{id}/capacity")
    public ResponseEntity<?> updateStoreCapacity(@PathVariable(name = "id") @Positive int storeId, @RequestParam(name = "capacity") @Positive int cap, @RequestHeader(name = "Authorization") String token) {
        return adminStoreService.updateStoreCapacity(storeId, cap, token);
    }
}
