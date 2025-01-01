package com.example.demo.dto;

import java.time.LocalDateTime;

public class AppointmentDTO {
    private final Long id;
    private final String patientName;
    private final String patientTcNo;
    private final String doctorName;
    private final String hospitalName;
    private final String departmentName;
    private final LocalDateTime appointmentTime;
    private final String status;

    // Constructor
    public AppointmentDTO(Long id, String patientName, String patientTcNo, 
                         String doctorName, String hospitalName, String departmentName, 
                         LocalDateTime appointmentTime, String status) {
        this.id = id;
        this.patientName = patientName;
        this.patientTcNo = patientTcNo;
        this.doctorName = doctorName;
        this.hospitalName = hospitalName;
        this.departmentName = departmentName;
        this.appointmentTime = appointmentTime;
        this.status = status;
    }

    // Getter metodları
    public Long getId() { return id; }
    public String getPatientName() { return patientName; }
    public String getPatientTcNo() { return patientTcNo; }
    public String getDoctorName() { return doctorName; }
    public String getHospitalName() { return hospitalName; }
    public String getDepartmentName() { return departmentName; }
    public LocalDateTime getAppointmentTime() { return appointmentTime; }
    public String getStatus() { return status; }
} 