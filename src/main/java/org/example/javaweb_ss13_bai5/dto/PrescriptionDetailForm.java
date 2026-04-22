package org.example.javaweb_ss13_bai5.dto;

public class PrescriptionDetailForm {

    private String medicineName;
    private Integer quantity;

    public PrescriptionDetailForm() {
    }

    public String getMedicineName() {
        return medicineName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
