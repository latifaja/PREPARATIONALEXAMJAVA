package com.emaple.preparationalexamjava;

import java.util.List;

public interface ICabinetMetier {
    List<Patient> afficherPatients();
    void ajouterPatient(Patient patient);
    List<Patient> chercherPatients(String motCle);
    void supprimerPatient(int idPatient);
    List<Consultation> consultationsParPatient(int idPatient);

    void ajouterMedecin(Medecin medecin);
    List<Medecin> afficherMedecins();
    void supprimerMedecin(int idMedecin);
    List<Consultation> consultationsParMedecin(int idMedecin);

    void ajouterConsultation(Consultation consultation);
    List<Consultation> afficherConsultations();
    void supprimerConsultation(int idConsultation);
}
