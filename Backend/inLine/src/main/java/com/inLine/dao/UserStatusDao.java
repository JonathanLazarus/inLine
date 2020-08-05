package com.inLine.dao;

import com.inLine.model.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStatusDao extends JpaRepository<UserStatus, Integer> {
}
