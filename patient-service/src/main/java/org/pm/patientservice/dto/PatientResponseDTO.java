package org.pm.patientservice.dto;


public class PatientResponseDTO {

    private String id;

    private String name;

    private String email;

    private String address;

    private String dob;


    public String getId() {
        return id;
    }

    public void setId(String uniqueId) {
        this.id = uniqueId;
    }

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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

}
