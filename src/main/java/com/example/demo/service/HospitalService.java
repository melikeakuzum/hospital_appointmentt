package com.example.demo.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.AppointmentRequest;
import com.example.demo.model.Department;
import com.example.demo.model.Doctor;
import com.example.demo.model.DoctorSchedule;
import com.example.demo.model.Hospital;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.DoctorRepository;
import com.example.demo.repository.DoctorScheduleRepository;
import com.example.demo.repository.HospitalRepository;

@Service
public class HospitalService {
    
    private static final Logger logger = LoggerFactory.getLogger(HospitalService.class);
    
    @Autowired
    private HospitalRepository hospitalRepository;
    
    @Autowired
    private DepartmentRepository departmentRepository;
    
    @Autowired
    private DoctorRepository doctorRepository;
    
    @Autowired
    private DoctorScheduleRepository scheduleRepository;

    @Transactional(readOnly = true)
    public List<Hospital> getAllHospitals() {
        return hospitalRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Department> getDepartmentsByHospital(Long hospitalId) {
        List<Department> departments = departmentRepository.findByHospitalId(hospitalId);
        logger.info("Hospital ID: {} - Found {} departments", hospitalId, departments.size());
        departments.forEach(d -> logger.info("Department: {}", d.getName()));
        return departments;
    }

    @Transactional(readOnly = true)
    public List<Doctor> getDoctorsByDepartment(Long departmentId) {
        List<Doctor> doctors = doctorRepository.findByDepartmentId(departmentId);
        logger.info("Department ID: {} - Found {} doctors", departmentId, doctors.size());
        doctors.forEach(d -> logger.info("Doctor: {} - {}", d.getFullName(), d.getSpecialization()));
        return doctors;
    }

    @Transactional(readOnly = true)
    public List<DoctorSchedule> getAvailableSlots(Long doctorId) {
        return scheduleRepository.findByDoctorIdAndIsAvailableOrderByStartTimeAsc(doctorId, true);
    }

    public void createAppointment(AppointmentRequest request) {
        // TODO: Implement appointment creation logic
    }
} 