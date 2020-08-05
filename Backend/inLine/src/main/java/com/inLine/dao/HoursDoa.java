package com.inLine.dao;

import com.inLine.model.Hours;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HoursDoa extends JpaRepository<Hours, Integer>  {
}
