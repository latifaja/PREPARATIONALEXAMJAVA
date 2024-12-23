package com.emaple.preparationalexamjava;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class CabinetConsoleApp {
    private static ICabinetMetier cabinetMetier;

    public static void main(String[] args) {
        try {
            cabinetMetier = new CabinetMetierImpl();
            Scanner scanner = new Scanner(System.in);
            int choix;

            do {
                afficherMenu();
                choix = scanner.nextInt();
                scanner.nextLine(); // Consommer le saut de ligne
                switch (choix) {
                    case 1 -> ajouterPatient(scanner);
                    case 2 -> rechercherPatients(scanner);
                    case 3 -> afficherConsultationsPatient(scanner);
                    case 4 -> supprimerPatient(scanner);
                    case 5 -> ajouterMedecin(scanner);
                    case 6 -> afficherMedecins();
                    case 7 -> afficherConsultationsMedecin(scanner);
                    case 8 -> supprimerMedecin(scanner);
                    case 9 -> ajouterConsultation(scanner);
                    case 10 -> afficherConsultations();
                    case 11 -> supprimerConsultation(scanner);
                    case 0 -> System.out.println("Au revoir !");
                    default -> System.out.println("Choix invalide.");
                }
            } while (choix != 0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void afficherMenu() {
        System.out.println("\n--- Menu Cabinet Médical ---");
        System.out.println("1. Ajouter un patient");
        System.out.println("2. Rechercher des patients");
        System.out.println("3. Afficher les consultations d'un patient");
        System.out.println("4. Supprimer un patient");
        System.out.println("5. Ajouter un médecin");
        System.out.println("6. Afficher la liste des médecins");
        System.out.println("7. Afficher les consultations d'un médecin");
        System.out.println("8. Supprimer un médecin");
        System.out.println("9. Ajouter une consultation");
        System.out.println("10. Afficher la liste des consultations");
        System.out.println("11. Supprimer une consultation");
        System.out.println("0. Quitter");
        System.out.print("Votre choix : ");
    }

    private static void ajouterPatient(Scanner scanner) {
        System.out.print("Nom : ");
        String nom = scanner.nextLine();
        System.out.print("Prénom : ");
        String prenom = scanner.nextLine();
        System.out.print("CIN : ");
        String cin = scanner.nextLine();
        System.out.print("Téléphone : ");
        String telephone = scanner.nextLine();
        System.out.print("Email : ");
        String email = scanner.nextLine();
        System.out.print("Date de naissance (AAAA-MM-JJ) : ");
        LocalDate dateNaissance = LocalDate.parse(scanner.nextLine());

        Patient patient = new Patient(0, nom, prenom, cin, telephone, email, dateNaissance);
        cabinetMetier.ajouterPatient(patient);
        System.out.println("Patient ajouté avec succès !");
    }

    private static void rechercherPatients(Scanner scanner) {
        System.out.print("Mot clé : ");
        String motCle = scanner.nextLine();
        List<Patient> patients = cabinetMetier.chercherPatients(motCle);
        if (patients.isEmpty()) {
            System.out.println("Aucun patient trouvé.");
        } else {
            for (Patient p : patients) {
                System.out.println(p);
            }
        }
    }

    private static void afficherConsultationsPatient(Scanner scanner) {
        System.out.print("ID du patient : ");
        int idPatient = scanner.nextInt();
        scanner.nextLine(); // Consommer le saut de ligne
        List<Consultation> consultations = cabinetMetier.consultationsParPatient(idPatient);
        if (consultations.isEmpty()) {
            System.out.println("Aucune consultation trouvée.");
        } else {
            for (Consultation c : consultations) {
                System.out.println(c);
            }
        }
    }

    private static void supprimerPatient(Scanner scanner) {
        System.out.print("ID du patient à supprimer : ");
        int idPatient = scanner.nextInt();
        scanner.nextLine(); // Consommer le saut de ligne
        cabinetMetier.supprimerPatient(idPatient);
        System.out.println("Patient supprimé avec succès !");
    }

    private static void ajouterMedecin(Scanner scanner) {
        System.out.print("Nom : ");
        String nom = scanner.nextLine();
        System.out.print("Prénom : ");
        String prenom = scanner.nextLine();
        System.out.print("Email : ");
        String email = scanner.nextLine();
        System.out.print("Téléphone : ");
        String tel = scanner.nextLine();

        Medecin medecin = new Medecin(0, nom, prenom, email, tel);
        cabinetMetier.ajouterMedecin(medecin);
        System.out.println("Médecin ajouté avec succès !");
    }

    private static void afficherMedecins() {
        List<Medecin> medecins = cabinetMetier.afficherMedecins();
        if (medecins.isEmpty()) {
            System.out.println("Aucun médecin trouvé.");
        } else {
            for (Medecin m : medecins) {
                System.out.println(m);
            }
        }
    }

    private static void afficherConsultationsMedecin(Scanner scanner) {
        System.out.print("ID du médecin : ");
        int idMedecin = scanner.nextInt();
        scanner.nextLine(); // Consommer le saut de ligne
        List<Consultation> consultations = cabinetMetier.consultationsParMedecin(idMedecin);
        if (consultations.isEmpty()) {
            System.out.println("Aucune consultation trouvée.");
        } else {
            for (Consultation c : consultations) {
                System.out.println(c);
            }
        }
    }

    private static void supprimerMedecin(Scanner scanner) {
        System.out.print("ID du médecin à supprimer : ");
        int idMedecin = scanner.nextInt();
        scanner.nextLine(); // Consommer le saut de ligne
        cabinetMetier.supprimerMedecin(idMedecin);
        System.out.println("Médecin supprimé avec succès !");
    }

    private static void ajouterConsultation(Scanner scanner) {
        System.out.print("ID du patient : ");
        int idPatient = scanner.nextInt();
        System.out.print("ID du médecin : ");
        int idMedecin = scanner.nextInt();
        System.out.print("Date de la consultation (AAAA-MM-JJ) : ");
        LocalDate dateConsultation = LocalDate.parse(scanner.next());

        Consultation consultation = new Consultation(0, idPatient, idMedecin, dateConsultation);
        cabinetMetier.ajouterConsultation(consultation);
        System.out.println("Consultation ajoutée avec succès !");
    }

    private static void afficherConsultations() {
        List<Consultation> consultations = cabinetMetier.afficherConsultations();
        if (consultations.isEmpty()) {
            System.out.println("Aucune consultation trouvée.");
        } else {
            for (Consultation c : consultations) {
                System.out.println(c);
            }
        }
    }

    private static void supprimerConsultation(Scanner scanner) {
        System.out.print("ID de la consultation à supprimer : ");
        int idConsultation = scanner.nextInt();
        scanner.nextLine(); // Consommer le saut de ligne
        cabinetMetier.supprimerConsultation(idConsultation);
        System.out.println("Consultation supprimée avec succès !");
    }
}

