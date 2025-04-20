package org.pm.patientservice.service;

import org.pm.patientservice.dto.PatientRequestDTO;
import org.pm.patientservice.dto.PatientResponseDTO;
import org.pm.patientservice.exception.EmailAlreadyExistsException;
import org.pm.patientservice.exception.PatientNotFoundException;
import org.pm.patientservice.grpc.BillingServiceGrpcClient;
import org.pm.patientservice.kafka.KafkaProducer;
import org.pm.patientservice.mapper.PatientMapper;
import org.pm.patientservice.model.Patient;
import org.pm.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PatientService {

    private PatientRepository patientRepository;
    private BillingServiceGrpcClient billingServiceGrpcClient;
    private KafkaProducer kafkaProducer;

    public PatientService(PatientRepository patientRepository,BillingServiceGrpcClient billingServiceGrpcClient,KafkaProducer kafkaProducer) {
        this.patientRepository = patientRepository;
        this.billingServiceGrpcClient = billingServiceGrpcClient;
        this.kafkaProducer = kafkaProducer;
    }

    public List<PatientResponseDTO> getAllPatients() {
        List<Patient> patients = patientRepository.findAll();
        return patients.stream().map(PatientMapper::toDTO).toList();
    }

    public PatientResponseDTO getPatientsById(UUID id) {
        return PatientMapper.toDTO(patientRepository.findById(id)
                .orElseThrow(()->new PatientNotFoundException("Patient is not found with id"+id)));
    }

    public PatientResponseDTO savePatient(PatientRequestDTO patientRequestDTO) {
        if(patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException("A patient with this email is already present"+patientRequestDTO.getEmail());
        }
        Patient patient = patientRepository.save(PatientMapper.toPatient(patientRequestDTO));

        billingServiceGrpcClient.createBillingAccount(patient.getId().toString(), patient.getName(), patient.getEmail(), patient.getAddress());

        kafkaProducer.sendEvent(patient);
        return PatientMapper.toDTO(patient);
    }

    public PatientResponseDTO updatePatient(UUID patientId,PatientRequestDTO patientRequestDTO) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(()->new PatientNotFoundException("Patient is not found with id"+patientId));
        if(patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(),patientId)) {
            throw new EmailAlreadyExistsException("A patient with this email is already present"+patientRequestDTO.getEmail());
        }
        if(patientRequestDTO.getName() != null) {
            patient.setName(patientRequestDTO.getName());
        }
        if(patientRequestDTO.getAddress() != null) {
            patient.setAddress(patientRequestDTO.getAddress());
        }
        if(patientRequestDTO.getEmail() != null) {
            patient.setEmail(patientRequestDTO.getEmail());
        }
        if(patientRequestDTO.getDateOfBirth() != null) {
            patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
        }
        patient = patientRepository.save(patient);
        return PatientMapper.toDTO(patient);
    }

    public void deletePatient(UUID patientId) {
        patientRepository.deleteById(patientId);
    }
}
