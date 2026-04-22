package org.example.javaweb_ss13_bai5.dto;

import java.util.ArrayList;
import java.util.List;

public class PrescriptionForm {

    private String patientCode;
    private String patientName;
    private List<PrescriptionDetailForm> details = new ArrayList<>();

    public PrescriptionForm() {
        details.add(new PrescriptionDetailForm());
    }

    public String getPatientCode() {
        return patientCode;
    }

    public String getPatientName() {
        return patientName;
    }

    public List<PrescriptionDetailForm> getDetails() {
        return details;
    }

    public void setPatientCode(String patientCode) {
        this.patientCode = patientCode;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public void setDetails(List<PrescriptionDetailForm> details) {
        this.details = details;
    }
}