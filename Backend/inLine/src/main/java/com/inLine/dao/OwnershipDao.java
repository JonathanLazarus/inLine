package com.inLine.dao;

import com.inLine.model.Ownership;
import com.inLine.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OwnershipDao extends JpaRepository<Ownership, Integer> {

    @Query("SELECT id FROM Ownership WHERE accountId = ?1 and storeId = ?2")
    Integer getStoreIdsAssociatedWithAdminId(int adminId, int storeId);
}
