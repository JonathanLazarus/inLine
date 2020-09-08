package com.inLine.service;

import com.inLine.dao.UserStatusDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class QueueService {

    @Autowired
    private UserStatusDao userStatusDao;


    public ResponseEntity<?> getQueueInfoByStoreId(int storeId) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public ResponseEntity<?> enqueueByStoreId(int storeId, String token) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public ResponseEntity<?> dequeueByStoreId(int storeId, String token) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public ResponseEntity<?> enterByStoreId(int storeId, String token) {
        return ResponseEntity.status(HttpStatus.OK).build();

    }

    public ResponseEntity<?> exitByStoreId(int storeId, String token) {
        return ResponseEntity.status(HttpStatus.OK).build();

    }

    public ResponseEntity<?> spotInQueue(int storeId, String token) {
        return ResponseEntity.status(HttpStatus.OK).build();

    }
}
