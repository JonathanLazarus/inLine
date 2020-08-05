package com.inLine.dao;

import com.inLine.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreDao extends JpaRepository<Store, Integer> {
}