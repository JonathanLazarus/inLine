package com.inLine.dao;

import com.inLine.model.Ownership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnershipDao extends JpaRepository<Ownership, Integer> {
}
