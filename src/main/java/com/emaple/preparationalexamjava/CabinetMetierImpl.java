package com.emaple.preparationalexamjava;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CabinetMetierImpl implements ICabinetMetier {
    private Connection connection;

    public CabinetMetierImpl() throws SQLException {
        connection = SingletonConnexionDB.getConnexion();
    }

    @Override
    public List<Patient> afficherPatients() {
        List<Patient> patients = new ArrayList<>();

        // Connexion à la base de données (remplacez ces informations par les vôtres)
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/votre_base", "votre_utilisateur", "votre_mot_de_passe")) {
            String sql = "SELECT * FROM patients"; // Remplacez 'patients' par le nom de votre table
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id_patient");
                    String nom = resultSet.getString("nom");
                    String prenom = resultSet.getString("prenom");
                    String cin = resultSet.getString("cin");
                    String telephone = resultSet.getString("telephone");
                    String email = resultSet.getString("email");
                    Date dateNaissance = resultSet.getDate("date_naissance");

                    // Créer un objet Patient et l'ajouter à la liste
                    Patient patient = new Patient(id, nom, prenom, cin, telephone, email, dateNaissance.toLocalDate());
                    patients.add(patient);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return patients;
    }

    @Override
    public void ajouterPatient(Patient patient) {
        String query = "INSERT INTO Patient (nom, prenom, cin, telephone, email, date_naissance) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, patient.getNom());
            ps.setString(2, patient.getPrenom());
            ps.setString(3, patient.getCin());
            ps.setString(4, patient.getTelephone());
            ps.setString(5, patient.getEmail());
            ps.setDate(6, Date.valueOf(patient.getDateNaissance()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Patient> chercherPatients(String motCle) {
        List<Patient> patients = new ArrayList<>();
        String query = "SELECT * FROM Patient WHERE nom LIKE ? OR prenom LIKE ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, "%" + motCle + "%");
            ps.setString(2, "%" + motCle + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                patients.add(new Patient(
                        rs.getInt("id_patient"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("cin"),
                        rs.getString("telephone"),
                        rs.getString("email"),
                        rs.getDate("date_naissance").toLocalDate()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }

    @Override
    public void supprimerPatient(int idPatient) {
        String query = "DELETE FROM Patient WHERE id_patient = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, idPatient);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Consultation> consultationsParPatient(int idPatient) {
        List<Consultation> consultations = new ArrayList<>();
        String query = "SELECT * FROM Consultation WHERE id_patient = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, idPatient);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                consultations.add(new Consultation(
                        rs.getInt("id_consultation"),
                        rs.getInt("id_patient"),
                        rs.getInt("id_medecin"),
                        rs.getDate("date_consultation").toLocalDate()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return consultations;
    }

    @Override
    public void ajouterMedecin(Medecin medecin) {
        String query = "INSERT INTO Medecin (nom, prenom, email, tel) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, medecin.getNom());
            ps.setString(2, medecin.getPrenom());
            ps.setString(3, medecin.getEmail());
            ps.setString(4, medecin.getTel());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Medecin> afficherMedecins() {
        List<Medecin> medecins = new ArrayList<>();
        String query = "SELECT * FROM Medecin";
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                medecins.add(new Medecin(
                        rs.getInt("id_medecin"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("tel")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medecins;
    }

    @Override
    public void supprimerMedecin(int idMedecin) {
        String query = "DELETE FROM Medecin WHERE id_medecin = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, idMedecin);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Consultation> consultationsParMedecin(int idMedecin) {
        List<Consultation> consultations = new ArrayList<>();
        String query = "SELECT c.id_consultation, c.date_consultation, p.id_patient, p.nom AS patient_nom, p.prenom AS patient_prenom, " +
                "p.cin, p.telephone, p.email, p.date_naissance, " +
                "m.id_medecin, m.nom AS medecin_nom, m.prenom AS medecin_prenom, m.email AS medecin_email, m.tel AS medecin_tel " +
                "FROM consultation c " +
                "JOIN patient p ON c.id_patient = p.id_patient " +
                "JOIN medecin m ON c.id_medecin = m.id_medecin " +
                "WHERE c.id_medecin = ?";
        try (Connection connection = SingletonConnexionDB.getConnexion();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Paramétrer la requête avec l'ID du médecin
            preparedStatement.setInt(1, idMedecin);

            // Exécuter la requête
            ResultSet resultSet = preparedStatement.executeQuery();

            // Parcourir les résultats
            while (resultSet.next()) {
                int idConsultation = resultSet.getInt("id_consultation");
                Date dateConsultation = resultSet.getDate("date_consultation");

                // Informations patient
                int idPatient = resultSet.getInt("id_patient");
                String patientNom = resultSet.getString("patient_nom");
                String patientPrenom = resultSet.getString("patient_prenom");
                String cin = resultSet.getString("cin");
                String telephone = resultSet.getString("telephone");
                String email = resultSet.getString("email");
                LocalDate dateNaissance = resultSet.getDate("date_naissance").toLocalDate();

                // Créer l'objet Patient
                Patient patient = new Patient(idPatient, patientNom, patientPrenom, cin, telephone, email, dateNaissance);

                // Informations médecin
                String medecinNom = resultSet.getString("medecin_nom");
                String medecinPrenom = resultSet.getString("medecin_prenom");
                String medecinEmail = resultSet.getString("medecin_email");
                String medecinTel = resultSet.getString("medecin_tel");
                Medecin medecin = new Medecin(idMedecin, medecinNom, medecinPrenom, medecinEmail, medecinTel);

                // Créer l'objet Consultation
                Consultation consultation = new Consultation(idConsultation,idPatient,idMedecin, dateConsultation.toLocalDate());
                consultations.add(consultation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return consultations;
    }



    @Override
    public void ajouterConsultation(Consultation consultation) {
        String query = "INSERT INTO Consultation (id_patient, id_medecin, date_consultation) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, consultation.getIdPatient());
            ps.setInt(2, consultation.getIdMedecin());
            ps.setDate(3, Date.valueOf(consultation.getDateConsultation()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Consultation> afficherConsultations() {
        List<Consultation> consultations = new ArrayList<>();
        String query = "SELECT * FROM Consultation";
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                consultations.add(new Consultation(
                        rs.getInt("id_consultation"),
                        rs.getInt("id_patient"),
                        rs.getInt("id_medecin"),
                        rs.getDate("date_consultation").toLocalDate()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return consultations;
    }

    @Override
    public void supprimerConsultation(int idConsultation) {
        String query = "DELETE FROM Consultation WHERE id_consultation = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, idConsultation);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

