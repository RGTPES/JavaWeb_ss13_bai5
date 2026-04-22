package org.example.javaweb_ss13_bai5.model;
import jakarta.persistence.*;

@Entity
@Table(name = "prescription_details")
public class PrescriptionDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "medicine_name", nullable = false, length = 100)
    private String medicineName;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prescription_id", nullable = false)
    private Prescription prescription;

    public PrescriptionDetail() {
    }

    public PrescriptionDetail(String medicineName, Integer quantity) {
        this.medicineName = medicineName;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }
}
