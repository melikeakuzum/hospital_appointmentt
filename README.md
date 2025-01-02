# Hastane Randevu Sistemi

Bu proje, hastaların online olarak hastane randevusu alabilmelerini sağlayan modern bir web uygulamasıdır.

## Teknolojik Altyapı

### Backend
- **Java 17**
- **Spring Boot 3.4.1**
  - Spring MVC
  - Spring Data JPA
  - Spring Transaction Management
- **MySQL 8.x** (Veritabanı)
- **Hibernate ORM** (JPA implementasyonu)
- **Maven** (Bağımlılık yönetimi ve proje yapılandırması)

### Frontend
- **Thymeleaf** (Server-side template engine)
- **Bootstrap 5.3.0** (UI framework)
- **Font Awesome 6.0.0** (İkonlar)
- **JavaScript** (Vanilla JS ile AJAX işlemleri)

## Özellikler

1. **Randevu Yönetimi**
   - Hastane, bölüm ve doktor seçimi
   - Müsait randevu saatlerini görüntüleme
   - Randevu oluşturma ve iptal etme
   - TC kimlik no ile randevu sorgulama

2. **Veri Modeli**
   - Hastane-Bölüm-Doktor hiyerarşisi
   - Hasta bilgileri yönetimi
   - Randevu kayıtları
   - Doktor çalışma saatleri

3. **Güvenlik ve Doğrulama**
   - Input validasyonu
   - TC kimlik no doğrulama
   - Transactional işlem güvenliği

## Veritabanı Şeması

```sql
hospital_db
├── hospital
│   ├── id
│   ├── name
│   └── address
├── department
│   ├── id
│   ├── name
│   └── hospital_id
├── doctor
│   ├── id
│   ├── full_name
│   ├── specialization
│   └── department_id
├── doctor_schedule
│   ├── id
│   ├── doctor_id
│   ├── start_time
│   ├── end_time
│   └── is_available
├── patient
│   ├── tc_no (PK)
│   ├── full_name
│   ├── phone
│   └── email
└── appointment
    ├── id
    ├── patient_tc
    ├── doctor_id
    ├── appointment_time
    └── status
```

## API Endpoints

### Randevu İşlemleri
- `GET /appointments` - Randevu formu sayfası
- `POST /appointments/create` - Yeni randevu oluşturma
- `GET /appointments/api/patient/{tcNo}` - TC no ile randevu sorgulama
- `DELETE /appointments/api/appointments/{id}` - Randevu iptal etme

### Hastane Bilgileri
- `GET /appointments/api/departments/{hospitalId}` - Hastaneye ait bölümleri getirme
- `GET /appointments/api/doctors/{departmentId}` - Bölüme ait doktorları getirme
- `GET /appointments/api/schedules/{doctorId}` - Doktorun müsait saatlerini getirme

## Kurulum

1. MySQL veritabanını oluşturun
2. `application.properties` dosyasını düzenleyin
3. Projeyi Maven ile derleyin: `mvn clean install`
4. Uygulamayı çalıştırın: `java -jar target/demo-0.0.1-SNAPSHOT.jar`

## Geliştirme Ortamı

- IDE: Spring Tool Suite veya IntelliJ IDEA
- JDK 17
- MySQL Workbench
- Git (versiyon kontrolü)

## ÇIKTILAR

![Ekran görüntüsü 2025-01-02 025512](https://github.com/user-attachments/assets/c43098a8-6f91-428a-8dde-bf5441574ca4)
![Ekran görüntüsü 2025-01-02 025534](https://github.com/user-attachments/assets/c8eb7232-10cb-4226-8614-d09b729fd3a3)

Responsive tasarımı incelemek içim:
https://1drv.ms/v/s!Amdyvbx_nJGhg_AgHIEWHh0X8fdoKA?e=TdYPOc 

Siteyi incelemek için:
https://1drv.ms/v/s!Amdyvbx_nJGhg_Ah-cj1WAxbJwRGzA?e=dtKVcO
