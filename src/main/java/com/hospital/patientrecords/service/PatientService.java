
package com.hospital.patientrecords.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hospital.patientrecords.entity.Patient;
import com.hospital.patientrecords.repository.PatientRepository;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

   
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }


    public Patient addPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

  
    public Patient getPatientById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException("Patient not found with ID: " + id));
    }

   
    public List<Patient> searchPatient(String name) {
        return patientRepository
                .findByFullNameContainingIgnoreCase(name);
    }

    public Patient updatePatient(
            Long id,
            Patient updatedPatient,
            boolean allowMedicalHistory) {

        Patient existingPatient = getPatientById(id);

        existingPatient.setFullName(updatedPatient.getFullName());
        existingPatient.setAge(updatedPatient.getAge());
        existingPatient.setGender(updatedPatient.getGender());
        existingPatient.setContactNumber(updatedPatient.getContactNumber());
        existingPatient.setAddress(updatedPatient.getAddress());

        if (allowMedicalHistory) {
            existingPatient.setMedicalHistory(
                    updatedPatient.getMedicalHistory());
        }

        return patientRepository.save(existingPatient);
    }
  
    public void deletePatient(Long id) {
        Patient patient = getPatientById(id);
        patientRepository.delete(patient);
    }
}
