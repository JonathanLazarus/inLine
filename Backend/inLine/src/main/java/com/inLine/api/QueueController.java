package com.inLine.api;

import com.inLine.service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/queue")
public class QueueController {

    @Autowired
    QueueService queueService;

    @GetMapping(path = "/{id}/info")
    public ResponseEntity<?> getQueueInfoByStoreId(@PathVariable("id") int storeId){
        return queueService.getQueueInfoByStoreId(storeId);
    }

    @PutMapping(path = "/{id}/enqueue")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> enqueueByStoreId(@PathVariable("id") int storeId, @RequestHeader(name = "Authorization") String token) {
        return queueService.enqueueByStoreId(storeId, token);
    }

    @PutMapping(path = "/{id}/dequeue")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> dequeueByStoreId(@PathVariable("id") int storeId, @RequestHeader(name = "Authorization") String token) {
        return queueService.dequeueByStoreId(storeId, token);
    }

    @PutMapping(path = "/{id}/enter")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> enterByStoreId(@PathVariable("id") int storeId, @RequestHeader(name = "Authorization") String token) {
        return queueService.enterByStoreId(storeId, token);
    }

    @PutMapping(path = "/{id}/exit")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> exitByStoreId(@PathVariable("id") int storeId, @RequestHeader(name = "Authorization") String token) {
        return queueService.exitByStoreId(storeId, token);
    }

    @GetMapping(path = "/{id}/spot")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> spotInQueue(@PathVariable("id") int storeId, @RequestHeader(name = "Authorization") String token) {
        return queueService.spotInQueue(storeId, token);
    }


}
