package org.pm.patientservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.pm.patientservice.dto.validators.CreatePatientValidatorGroup;

public class PatientRequestDTO {
    @NotBlank(message = "Name is missing")
    @Size(max=100,message = "Name should not exceed 100 characters")
    private String name;

    @NotBlank(message = "Email is missing")
    @Email(message = "Provide a valid email")
    private String email;

    @NotBlank(message = "Address is missing")
    private String address;

    @NotBlank(message = "DateOfBirth is required")
    private String dateOfBirth;

    @NotBlank(groups = {CreatePatientValidatorGroup.class}, message = "RegisteredDate is required")
    private String registeredDate;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(String registeredDate) {
        this.registeredDate = registeredDate;
    }
}
