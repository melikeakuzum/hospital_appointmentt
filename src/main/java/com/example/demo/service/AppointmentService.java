package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.AppointmentDTO;
import com.example.demo.dto.AppointmentRequest;
import com.example.demo.model.Appointment;
import com.example.demo.model.DoctorSchedule;
import com.example.demo.model.Patient;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.DoctorScheduleRepository;
import com.example.demo.repository.PatientRepository;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorScheduleRepository scheduleRepository;

    @Transactional
    public Appointment createAppointment(AppointmentRequest request) {
        // Hasta kaydı oluştur veya güncelle
        Patient patient = patientRepository.findById(request.getTcNo())
            .orElse(new Patient());
        
        patient.setTcNo(request.getTcNo());
        patient.setFullName(request.getFullName());
        patient.setPhone(request.getPhone());
        patient.setEmail(request.getEmail());
        
        patientRepository.save(patient);

        // Seçilen randevu saatini al
        DoctorSchedule schedule = scheduleRepository.findById(request.getScheduleId())
            .orElseThrow(() -> new RuntimeException("Randevu saati bulunamadı"));

        // Randevu oluştur
        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(schedule.getDoctor());
        appointment.setAppointmentTime(schedule.getStartTime());
        appointment.setStatus("ACTIVE");

        appointmentRepository.save(appointment);

        // Randevu saatini müsait değil olarak işaretle
        schedule.setAvailable(false);
        scheduleRepository.save(schedule);

        return appointment;
    }

    @Transactional(readOnly = true)
    public List<AppointmentDTO> getPatientAppointments(String tcNo) {
        List<Appointment> appointments = appointmentRepository.findByPatient_TcNoOrderByAppointmentTimeDesc(tcNo);
        return appointments.stream()
            .map(apt -> new AppointmentDTO(
                apt.getId(),
                apt.getPatient().getFullName(),
                apt.getPatient().getTcNo(),
                apt.getDoctor().getFullName(),
                apt.getDoctor().getDepartment().getHospital().getName(),
                apt.getDoctor().getDepartment().getName(),
                apt.getAppointmentTime(),
                apt.getStatus()
            ))
            .collect(Collectors.toList());
    }

    @Transactional
    public void deleteAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Randevu bulunamadı"));
        
        // Randevu saatini tekrar müsait yap
        DoctorSchedule schedule = scheduleRepository.findByDoctorAndStartTime(
            appointment.getDoctor(), 
            appointment.getAppointmentTime()
        );
        if (schedule != null) {
            schedule.setAvailable(true);
            scheduleRepository.save(schedule);
        }
        
        appointmentRepository.deleteById(id);
    }
} 