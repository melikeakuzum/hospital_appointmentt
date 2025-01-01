SET FOREIGN_KEY_CHECKS = 0;

-- Önce tabloları temizleyelim
TRUNCATE TABLE doctor_schedule;
TRUNCATE TABLE appointment;
TRUNCATE TABLE doctor;
TRUNCATE TABLE department;
TRUNCATE TABLE hospital;
TRUNCATE TABLE patient;

SET FOREIGN_KEY_CHECKS = 1;

-- Hastaneler
INSERT INTO hospital (id, name, address) VALUES 
(1, 'Ozel Acibadem Hastanesi', 'Istanbul Acibadem'),
(2, 'Ozel Kurtkoy Hastanesi', 'Istanbul Kurtkoy'),
(3, 'Ozel Ulus Hastanesi', 'Ankara Ulus');

-- Poliklinikler
INSERT INTO department (id, name, hospital_id) VALUES 
-- Ozel Acibadem Hastanesi
(1, 'Dahiliye', 1),
(2, 'Kardiyoloji', 1),
(3, 'Goz Hastaliklari', 1),
(4, 'Ortopedi', 1),

-- Ozel Kurtkoy Hastanesi
(5, 'Dahiliye', 2),
(6, 'Noroloji', 2),
(7, 'Cocuk Hastaliklari', 2),
(8, 'Kadin Hastaliklari', 2),

-- Ozel Ulus Hastanesi
(9, 'Genel Cerrahi', 3),
(10, 'Kardiyoloji', 3),
(11, 'Gogus Hastaliklari', 3),
(12, 'Uroloji', 3);

-- Doktorlar
INSERT INTO doctor (id, full_name, specialization, department_id) VALUES 
-- Dahiliye (1. Hastane)
(1, 'Dr. Ahmet Yilmaz', 'Ic Hastaliklari Uzmani', 1),
(2, 'Dr. Mehmet Demir', 'Ic Hastaliklari Uzmani', 1),
(3, 'Dr. Ayse Kaya', 'Ic Hastaliklari Uzmani', 1),

-- Kardiyoloji (1. Hastane)
(4, 'Dr. Fatma Sahin', 'Kardiyoloji Uzmani', 2),
(5, 'Dr. Ali Yildiz', 'Kardiyoloji Uzmani', 2),

-- Goz Hastaliklari (1. Hastane)
(6, 'Dr. Zeynep Celik', 'Goz Hastaliklari Uzmani', 3),
(7, 'Dr. Mustafa Aydin', 'Goz Hastaliklari Uzmani', 3),

-- Ortopedi (1. Hastane)
(8, 'Dr. Hakan Ozturk', 'Ortopedi Uzmani', 4),
(9, 'Dr. Selin Arslan', 'Ortopedi Uzmani', 4);

-- Doktor Çalışma Saatleri (Her doktor için)
INSERT INTO doctor_schedule (doctor_id, start_time, end_time, is_available) 
SELECT 
    d.id,
    TIMESTAMP(CURRENT_DATE(), '09:00:00') + INTERVAL (n.num * 45) MINUTE,
    TIMESTAMP(CURRENT_DATE(), '09:00:00') + INTERVAL ((n.num + 1) * 45) MINUTE,
    true
FROM doctor d
CROSS JOIN (
    SELECT 0 AS num UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION 
    SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7
) n
WHERE 
    TIMESTAMP(CURRENT_DATE(), '09:00:00') + INTERVAL (n.num * 45) MINUTE < TIMESTAMP(CURRENT_DATE(), '15:00:00');

-- Yarın için randevu saatleri
INSERT INTO doctor_schedule (doctor_id, start_time, end_time, is_available) 
SELECT 
    d.id,
    TIMESTAMP(CURRENT_DATE() + INTERVAL 1 DAY, '09:00:00') + INTERVAL (n.num * 45) MINUTE,
    TIMESTAMP(CURRENT_DATE() + INTERVAL 1 DAY, '09:00:00') + INTERVAL ((n.num + 1) * 45) MINUTE,
    true
FROM doctor d
CROSS JOIN (
    SELECT 0 AS num UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION 
    SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7
) n
WHERE 
    TIMESTAMP(CURRENT_DATE() + INTERVAL 1 DAY, '09:00:00') + INTERVAL (n.num * 45) MINUTE < TIMESTAMP(CURRENT_DATE() + INTERVAL 1 DAY, '15:00:00');