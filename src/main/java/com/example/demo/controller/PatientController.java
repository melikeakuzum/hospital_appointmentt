package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.Patient;
import com.example.demo.service.PatientService;

@Controller
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping("/save")
    @ResponseBody
    public Patient savePatient(Patient patient) {
        return patientService.savePatient(patient);
    }

    @GetMapping("/check")
    @ResponseBody
    public Patient getPatientByTcNo(String tcNo) {
        return patientService.findByTcNo(tcNo);
    }
} 