package com.example.demo.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String specialization;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id")
    private Department department;

    @JsonManagedReference
    @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY)
    private List<DoctorSchedule> schedules;

    // Parametresiz constructor
    public Doctor() {
    }

    // ID, fullName ve specialization için constructor
    public Doctor(Long id, String fullName, String specialization) {
        this.id = id;
        this.fullName = fullName;
        this.specialization = specialization;
    }

    // Getter ve Setter metodları
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }

    public List<DoctorSchedule> getSchedules() { return schedules; }
    public void setSchedules(List<DoctorSchedule> schedules) { this.schedules = schedules; }
} 