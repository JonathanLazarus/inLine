package com.inLine.service;

import com.inLine.SecurityAndJWT.jwt.JwtTokenUtil;
import com.inLine.dao.AccountDao;
import com.inLine.dao.HoursDao;
import com.inLine.dao.OwnershipDao;
import com.inLine.dao.StoreDao;
import com.inLine.model.Account;
import com.inLine.model.Hours;
import com.inLine.model.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service //for admin-only actions in regard to Store entity
public class AdminStoreService {

    @Autowired
    private AccountDao accountDao;
    @Autowired
    private StoreDao storeDao;
    @Autowired
    OwnershipDao ownershipDao;
    @Autowired
    private HoursDao hoursDao;
    @Autowired
    private JwtTokenUtil jwtUtil;

    public ResponseEntity<?> addStore(Store store, String token) {
        Account admin = getAccountFromToken(token);
        store.addManager(admin);
        storeDao.save(store);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    private Account getAccountFromToken(String token) {
        return accountDao.findById(getIdFromToken(token))
                .orElse(null);
    }
    private int getIdFromToken(String token) {
        return jwtUtil.getIdFromToken(token.substring(7));
    }
    private Integer getOwnershipRow(String token, int storeId) {
        return ownershipDao.getStoreIdsAssociatedWithAdminId(getAccountFromToken(token).getId(), storeId);
    }
    private Account getAccountFromEmail(String newAdminEmail) {
        return accountDao.selectUserByEmail(newAdminEmail)
                .orElse(null);
    }

    public ResponseEntity<?> deleteStoreById(int id, String token) {
        if(getOwnershipRow(token, id) == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Account does not have access to this store");
        storeDao.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public ResponseEntity<?> updateStoreById(int storeId, Store update, String token) {
        Store s = tokenHasAccessAndStoreExists(storeId, token);
        if(s == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        update.setId(storeId);
        storeDao.save(update);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public ResponseEntity<?> addAdminToStore(int storeId, String newAdminEmail, String token) {
        Store s = tokenHasAccessAndStoreExists(storeId, token);
        if(s == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        Account accountToAdd = getAccountFromEmail(newAdminEmail);
        if (accountToAdd == null || !accountToAdd.getLevel().equals("ADMIN")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Account with username doesn't exist or account is not admin type.");
        }
        s.addManager(accountToAdd);
        storeDao.save(s);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public ResponseEntity<?> removeAdminFromStore(int storeId, String removeAdminEmail, String token) {
        Store s = tokenHasAccessAndStoreExists(storeId, token);
        if(s == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        Account managerToRemove = s.getManagers().stream()
                .filter(manager -> manager.getEmail().equals(removeAdminEmail))
                .findFirst()
                .orElse(null);
        //check that the admin account is in fact associated with teh given store
        if(managerToRemove == null || s.getManagers().size() > 1) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        s.removeManager(managerToRemove);
        storeDao.save(s);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /*
     *@param storeId store to have its hours changed
     *@param newHours the new hours you want to set for that day
     *@param token JWT passed in header
     */
    public ResponseEntity<?> updateStoreHoursByDay(int storeId, Hours newHours, String token) {
        Store s = tokenHasAccessAndStoreExists(storeId, token);
        if(s == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        s.changeHours(newHours);
        storeDao.save(s);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //only returns a store if it exists in the database, and the admin associated with the token is a manager
    //otherwise returns null
    private Store tokenHasAccessAndStoreExists(int storeId, String token) {
        Store s = storeDao.findById(storeId).orElse(null);
        if(s == null) return null;
        boolean accessGranted = s.getManagers().stream().anyMatch(manager -> manager.getId() == getIdFromToken(token));
        return accessGranted ? s : null;
    }

    public ResponseEntity<?> updateStoreCapacity(int storeId, int cap, String token) {
        Store s = tokenHasAccessAndStoreExists(storeId, token);
        if(s == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        s.setCapacity(cap);
        storeDao.save(s);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
