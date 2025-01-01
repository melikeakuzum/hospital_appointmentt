package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    
    // Sadece ID ve name alanlarını getir
    @Query("SELECT new Department(d.id, d.name) FROM Department d WHERE d.hospital.id = :hospitalId")
    List<Department> findByHospitalId(@Param("hospitalId") Long hospitalId);
} 