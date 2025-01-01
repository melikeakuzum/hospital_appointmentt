package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dto.AppointmentDTO;
import com.example.demo.dto.AppointmentRequest;
import com.example.demo.model.Appointment;
import com.example.demo.model.Department;
import com.example.demo.model.Doctor;
import com.example.demo.model.DoctorSchedule;
import com.example.demo.service.AppointmentService;
import com.example.demo.service.HospitalService;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private HospitalService hospitalService;

    @GetMapping
    public String showAppointmentForm(Model model) {
        model.addAttribute("hospitals", hospitalService.getAllHospitals());
        return "appointment-form";
    }

    @GetMapping("/api/departments/{hospitalId}")
    @ResponseBody
    public List<Department> getDepartments(@PathVariable Long hospitalId) {
        return hospitalService.getDepartmentsByHospital(hospitalId);
    }

    @GetMapping("/api/doctors/{departmentId}")
    @ResponseBody
    public List<Doctor> getDoctors(@PathVariable Long departmentId) {
        return hospitalService.getDoctorsByDepartment(departmentId);
    }

    @GetMapping("/api/schedules/{doctorId}")
    @ResponseBody
    public List<DoctorSchedule> getAvailableSlots(@PathVariable Long doctorId) {
        return hospitalService.getAvailableSlots(doctorId);
    }

    @PostMapping("/create")
    public String createAppointment(@ModelAttribute AppointmentRequest request, RedirectAttributes redirectAttributes) {
        try {
            Appointment appointment = appointmentService.createAppointment(request);
            
            // Hibernate proxy'lerini initialize edelim
            Hibernate.initialize(appointment.getDoctor().getDepartment());
            Hibernate.initialize(appointment.getDoctor().getDepartment().getHospital());
            
            redirectAttributes.addFlashAttribute("success", true);
            redirectAttributes.addFlashAttribute("appointment", appointment);
            return "redirect:/appointments";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Randevu oluşturulurken bir hata oluştu: " + e.getMessage());
            return "redirect:/appointments";
        }
    }

    @GetMapping("/api/patient/{tcNo}")
    @ResponseBody
    public ResponseEntity<?> getPatientAppointments(@PathVariable String tcNo) {
        try {
            List<AppointmentDTO> appointments = appointmentService.getPatientAppointments(tcNo);
            if (appointments.isEmpty()) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "Randevu bulunamadı");
                return ResponseEntity.ok(response);
            }
            return ResponseEntity.ok(appointments);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("message", "Randevular yüklenirken bir hata oluştu: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @DeleteMapping("/api/appointments/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteAppointment(@PathVariable Long id) {
        try {
            appointmentService.deleteAppointment(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Randevu silinirken bir hata oluştu: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}