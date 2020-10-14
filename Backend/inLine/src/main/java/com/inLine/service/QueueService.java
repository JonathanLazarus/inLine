package com.inLine.service;

import com.inLine.SecurityAndJWT.jwt.JwtTokenUtil;
import com.inLine.dao.AccountDao;
import com.inLine.dao.StoreDao;
import com.inLine.dao.UserStatusDao;
import com.inLine.model.Account;
import com.inLine.model.Store;
import com.inLine.model.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class QueueService {

    @Autowired
    private UserStatusDao userStatusDao;
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private StoreDao storeDao;
    @Autowired
    private JwtTokenUtil jwtUtil;


    public ResponseEntity<?> getQueueInfoByStoreId(int storeId) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public ResponseEntity<?> enqueueByStoreId(int storeId, String token) {
        Account user = getAccountFromToken(token);
        Store store = storeDao.findById(storeId).orElse(null);
        if (user == null || store == null || user.getLevel().equals("ADMIN")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        userStatusDao.save(store.enqueue(user));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public ResponseEntity<?> dequeueByStoreId(int storeId, String token) {
        Account user = getAccountFromToken(token);
        Store store = storeDao.findById(storeId).orElse(null);
        if (user == null || store == null || user.getLevel().equals("ADMIN")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        List<UserStatus> l = store.dequeue(user);
        for(UserStatus us : l){
            if (us != null) userStatusDao.save(us);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public ResponseEntity<?> enterByStoreId(int storeId, String token) {
        Account user = getAccountFromToken(token);
        Store store = storeDao.findById(storeId).orElse(null);
        if (user == null || store == null || user.getLevel().equals("ADMIN")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        for(UserStatus us : store.enShop(user)){
            userStatusDao.save(us);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public ResponseEntity<?> exitByStoreId(int storeId, String token) {
        return ResponseEntity.status(HttpStatus.OK).build();

    }

    public ResponseEntity<?> spotInQueue(int storeId, String token) {
        return ResponseEntity.status(HttpStatus.OK).build();

    }

    private Account getAccountFromToken(String token) {
        return accountDao.findById(getIdFromToken(token))
            .orElse(null);
    }

    private int getIdFromToken(String token) {
        return jwtUtil.getIdFromToken(token.substring(7));
    }
}
