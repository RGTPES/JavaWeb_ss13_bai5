package org.example.javaweb_ss13_bai5.service;

import org.example.javaweb_ss13_bai5.dto.PrescriptionDetailForm;
import org.example.javaweb_ss13_bai5.dto.PrescriptionForm;
import org.example.javaweb_ss13_bai5.model.Prescription;
import org.example.javaweb_ss13_bai5.model.PrescriptionDetail;
import org.example.javaweb_ss13_bai5.repository.PrescriptionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;

    public PrescriptionService(PrescriptionRepository prescriptionRepository) {
        this.prescriptionRepository = prescriptionRepository;
    }

    public List<Prescription> findAll() {
        return prescriptionRepository.findAll();
    }

    public List<Prescription> searchByPatientCode(String patientCode) {
        if (patientCode == null || patientCode.trim().isEmpty()) {
            return prescriptionRepository.findAll();
        }
        return prescriptionRepository.findByPatientCode(patientCode.trim());
    }

    public List<String> validate(PrescriptionForm form) {
        List<String> errors = new ArrayList<>();

        if (form.getPatientCode() == null || form.getPatientCode().trim().isEmpty()) {
            errors.add("Mã bệnh nhân không được để trống");
        }

        if (form.getPatientName() == null || form.getPatientName().trim().isEmpty()) {
            errors.add("Tên bệnh nhân không được để trống");
        }

        if (form.getDetails() == null || form.getDetails().isEmpty()) {
            errors.add("Đơn thuốc phải có ít nhất 1 chi tiết");
            return errors;
        }

        for (int i = 0; i < form.getDetails().size(); i++) {
            PrescriptionDetailForm d = form.getDetails().get(i);

            if (d.getMedicineName() == null || d.getMedicineName().trim().isEmpty()) {
                errors.add("Tên thuốc ở dòng " + (i + 1) + " không được để trống");
            }

            if (d.getQuantity() == null) {
                errors.add("Số lượng ở dòng " + (i + 1) + " không được để trống");
            } else if (d.getQuantity() < 0) {
                errors.add("Số lượng thuốc ở dòng " + (i + 1) + " không được âm");
            }
        }

        return errors;
    }

    public void save(PrescriptionForm form) {
        Prescription prescription = new Prescription();
        prescription.setPatientCode(form.getPatientCode().trim());
        prescription.setPatientName(form.getPatientName().trim());

        for (PrescriptionDetailForm d : form.getDetails()) {
            PrescriptionDetail detail = new PrescriptionDetail();
            detail.setMedicineName(d.getMedicineName().trim());
            detail.setQuantity(d.getQuantity());
            prescription.addDetail(detail);
        }

        prescriptionRepository.save(prescription);
    }
}