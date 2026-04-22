package org.example.javaweb_ss13_bai5.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "prescriptions")
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "patient_code", nullable = false, length = 50)
    private String patientCode;

    @Column(name = "patient_name", nullable = false, length = 100)
    private String patientName;

    @OneToMany(
            mappedBy = "prescription",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<PrescriptionDetail> details = new ArrayList<>();

    public Prescription() {
    }

    public Prescription(String patientCode, String patientName) {
        this.patientCode = patientCode;
        this.patientName = patientName;
    }

    public void addDetail(PrescriptionDetail detail) {
        details.add(detail);
        detail.setPrescription(this);
    }

    public void removeDetail(PrescriptionDetail detail) {
        details.remove(detail);
        detail.setPrescription(null);
    }

    public Long getId() {
        return id;
    }

    public String getPatientCode() {
        return patientCode;
    }

    public String getPatientName() {
        return patientName;
    }

    public List<PrescriptionDetail> getDetails() {
        return details;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPatientCode(String patientCode) {
        this.patientCode = patientCode;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public void setDetails(List<PrescriptionDetail> details) {
        this.details = details;
    }
}
