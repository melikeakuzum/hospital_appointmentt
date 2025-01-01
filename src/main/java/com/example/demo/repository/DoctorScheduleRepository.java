package com.example.demo.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Doctor;
import com.example.demo.model.DoctorSchedule;

public interface DoctorScheduleRepository extends JpaRepository<DoctorSchedule, Long> {
    @Query("SELECT ds FROM DoctorSchedule ds " +
           "WHERE ds.doctor.id = :doctorId " +
           "AND ds.isAvailable = :isAvailable " +
           "AND ds.startTime >= CURRENT_TIMESTAMP " +
           "ORDER BY ds.startTime ASC")
    List<DoctorSchedule> findByDoctorIdAndIsAvailableOrderByStartTimeAsc(
        @Param("doctorId") Long doctorId, 
        @Param("isAvailable") boolean isAvailable
    );

    DoctorSchedule findByDoctorAndStartTime(Doctor doctor, LocalDateTime startTime);
} 