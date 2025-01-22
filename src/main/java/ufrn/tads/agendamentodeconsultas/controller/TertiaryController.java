package ufrn.tads.agendamentodeconsultas.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import ufrn.tads.agendamentodeconsultas.App;

public class TertiaryController {

    @FXML
    private Button agendarButton;

    @FXML
    private Button btnAlterar;

    @FXML
    private Button btnAlterarMed;

    @FXML
    private Button btnCadastrar;

    @FXML
    private Button btnCadastrarMed;

    @FXML
    private Button btnExcluir;

    @FXML
    private Button btnExcluirMed;

    @FXML
    private Button btnLimpar;

    @FXML
    private Button btnLimparMed;

    @FXML
    private Button sairButton1;

    @FXML
    private TextField txtCPF;

    @FXML
    private TextField txtCRM;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtEnd;

    @FXML
    private TextField txtEsp;

    @FXML
    private TextField txtNasc;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtNomeMed;

    @FXML
    private TextField txtTel;

    @FXML
    private TextField txtTelMed;

    @FXML
    void switchToPrimary(ActionEvent event) throws Exception{
        App.setRoot("PrimaryController");
    }

    @FXML
    private void switchToSecondaryButton(ActionEvent event) throws Exception {
        App.setRoot("SecondaryController");
    }
}
