package org.example.javaweb_ss13_bai5.repository;

import org.example.javaweb_ss13_bai5.model.Prescription;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PrescriptionRepository {

    private final SessionFactory sessionFactory;

    public PrescriptionRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Prescription> findAll() {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            List<Prescription> prescriptions = session.createQuery(
                    "select distinct p from Prescription p left join fetch p.details order by p.id desc",
                    Prescription.class
            ).getResultList();

            tx.commit();
            return prescriptions;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }

    public List<Prescription> findByPatientCode(String patientCode) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            List<Prescription> prescriptions = session.createQuery(
                            "select distinct p from Prescription p left join fetch p.details " +
                                    "where p.patientCode like :patientCode order by p.id desc",
                            Prescription.class
                    )
                    .setParameter("patientCode", "%" + patientCode + "%")
                    .getResultList();

            tx.commit();
            return prescriptions;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }

    public Prescription save(Prescription prescription) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            session.persist(prescription);

            tx.commit();
            return prescription;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }
}