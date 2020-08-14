package com.inLine.dao;

import com.inLine.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StoreDao extends JpaRepository<Store, Integer> {

    @Query("FROM Store WHERE UPPER(name) LIKE ?#{[0].toUpperCase()}%")
    List<Store> findStoresByPrefix(String prefix);
}