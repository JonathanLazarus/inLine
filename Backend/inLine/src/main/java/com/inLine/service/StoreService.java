package com.inLine.service;

import com.inLine.dao.StoreDao;
import com.inLine.model.Store;
import com.inLine.model.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreService {

    @Autowired
    private StoreDao storeDao;

    public Store getStoreById(int id) {
        return storeDao.findById(id).
                orElse(null);
    }

    public List<Store> getStoresWithPrefix(String prefix) {
        return storeDao.findStoresByPrefix(prefix);
    }

    public List<UserStatus> getQueueById(int id) {
        Store s = storeDao.findById(id).orElse(null);
        if(s == null) return null;
        return s.getQueue();
    }
}
