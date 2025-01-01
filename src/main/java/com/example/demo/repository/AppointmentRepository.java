package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Query("SELECT DISTINCT a FROM Appointment a " +
           "JOIN FETCH a.patient p " +
           "JOIN FETCH a.doctor d " +
           "JOIN FETCH d.department dept " +
           "JOIN FETCH dept.hospital h " +
           "WHERE p.tcNo = :tcNo " +
           "ORDER BY a.appointmentTime DESC")
    List<Appointment> findByPatient_TcNoOrderByAppointmentTimeDesc(@Param("tcNo") String tcNo);
} 