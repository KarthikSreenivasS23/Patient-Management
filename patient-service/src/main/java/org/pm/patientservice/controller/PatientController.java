package org.pm.patientservice.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.pm.patientservice.dto.PatientRequestDTO;
import org.pm.patientservice.dto.PatientResponseDTO;
import org.pm.patientservice.dto.validators.CreatePatientValidatorGroup;
import org.pm.patientservice.model.Patient;
import org.pm.patientservice.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
@Tag(name = "Patient", description = "API doc for managing Patients")
public class PatientController {

    private PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    @Operation(summary = "Get all patients")
    public ResponseEntity<List<PatientResponseDTO>> getAllPatients() {
        List<PatientResponseDTO> patientRecordDTOS =patientService.getAllPatients();
        return new ResponseEntity<>(patientRecordDTOS, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get patient by id")
    public ResponseEntity<PatientResponseDTO> getPatientsById(@PathVariable String id) {
        PatientResponseDTO patientRecordDTOS = patientService.getPatientsById(UUID.fromString(id));
        return new ResponseEntity<>(patientRecordDTOS, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "add patient to record")
    public ResponseEntity<PatientResponseDTO> createPatient(@Validated({Default.class, CreatePatientValidatorGroup.class}) @RequestBody PatientRequestDTO patientRequestDTO) {
        PatientResponseDTO patientResponseDTO = patientService.savePatient(patientRequestDTO);
        return new ResponseEntity<>(patientResponseDTO, HttpStatus.OK);
    }

    @PutMapping("/{patientId}")
    @Operation(summary = "update patient record")
    public ResponseEntity<PatientResponseDTO> updatePatient(@Validated({Default.class}) @RequestBody PatientRequestDTO patientRequestDTO,@PathVariable String patientId) {
        PatientResponseDTO patientResponseDTO = patientService.updatePatient(UUID.fromString(patientId),patientRequestDTO);
        return ResponseEntity.ok().body(patientResponseDTO);
    }

    @DeleteMapping
    @Operation(summary = "delete patient from record")
    public ResponseEntity<Void> deletePatientRecord(@RequestParam UUID id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
