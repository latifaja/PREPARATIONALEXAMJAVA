package com.emaple.preparationalexamjava;

import java.time.LocalDate;

public class Consultation {
    private int idConsultation;
    private int idPatient;
    private int idMedecin;
    private LocalDate dateConsultation;

    public Consultation(int idConsultation, int idPatient, int idMedecin, LocalDate dateConsultation) {
        this.idConsultation = idConsultation;
        this.idPatient = idPatient;
        this.idMedecin = idMedecin;
        this.dateConsultation = dateConsultation;
    }

    // Getters, setters, and toString
    public int getIdConsultation() {
        return idConsultation;
    }

    public void setIdConsultation(int idConsultation) {
        this.idConsultation = idConsultation;
    }

    public int getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(int idPatient) {
        this.idPatient = idPatient;
    }

    public int getIdMedecin() {
        return idMedecin;
    }

    public void setIdMedecin(int idMedecin) {
        this.idMedecin = idMedecin;
    }

    public LocalDate getDateConsultation() {
        return dateConsultation;
    }

    public void setDateConsultation(LocalDate dateConsultation) {
        this.dateConsultation = dateConsultation;
    }

    @Override
    public String toString() {
        return "Consultation{" +
                "idConsultation=" + idConsultation +
                ", idPatient=" + idPatient +
                ", idMedecin=" + idMedecin +
                ", dateConsultation=" + dateConsultation +
                '}';
    }
}

