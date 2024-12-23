package com.emaple.preparationalexamjava;



import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.Optional;


public class PatientController {
    @FXML
    private TableView<Patient> patientTable;
    @FXML
    private TableColumn<Patient, Integer> colId;
    @FXML
    private TableColumn<Patient, String> colNom;
    @FXML
    private TableColumn<Patient, String> colPrenom;
    @FXML
    private TableColumn<Patient, String> colCin;
    @FXML
    private TableColumn<Patient, String> colTelephone;

    private CabinetMetierImpl cabinetMetier;

    @FXML
    public void initialize() throws SQLException {
        cabinetMetier = new CabinetMetierImpl();

        // Configure columns using PropertyValueFactory
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colCin.setCellValueFactory(new PropertyValueFactory<>("cin"));
        colTelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));

        refreshTable();
    }

    private void refreshTable() {
        patientTable.getItems().setAll(cabinetMetier.afficherPatients());
    }

    @FXML
    public void handleAddPatient() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Ajouter Patient");
        dialog.setHeaderText("Entrez le nom du patient :");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(nom -> {
            Patient patient = new Patient();
            patient.setNom(nom);
            cabinetMetier.ajouterPatient(patient);
            refreshTable();
        });
    }

    @FXML
    public void handleSearchPatient() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Rechercher Patient");
        dialog.setHeaderText("Entrez un mot clé :");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(motCle -> {
            patientTable.getItems().setAll(cabinetMetier.chercherPatients(motCle));
        });
    }

    @FXML
    public void handleViewConsultations() {
        Patient selectedPatient = patientTable.getSelectionModel().getSelectedItem();
        if (selectedPatient != null) {
            System.out.println("Consultations du patient : " + selectedPatient.getNom());
        }
    }
}
