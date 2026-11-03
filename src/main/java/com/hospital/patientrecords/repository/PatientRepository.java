
package com.hospital.patientrecords.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hospital.patientrecords.entity.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

  
    java.util.List<Patient> findByFullNameContainingIgnoreCase(String fullName);

}
