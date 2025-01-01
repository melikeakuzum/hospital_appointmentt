package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    
    @Query("SELECT new Doctor(d.id, d.fullName, d.specialization) FROM Doctor d WHERE d.department.id = :departmentId")
    List<Doctor> findByDepartmentId(@Param("departmentId") Long departmentId);
} 