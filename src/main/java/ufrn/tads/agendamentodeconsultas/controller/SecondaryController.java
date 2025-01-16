package ufrn.tads.agendamentodeconsultas.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import ufrn.tads.agendamentodeconsultas.model.Paciente;

import java.net.URL;
import java.util.ResourceBundle;

public class SecondaryController implements Initializable {

    @FXML
    private Button btnAlterar;

    @FXML
    private Button btnCadastrar;

    @FXML
    private Button btnExcluir;

    @FXML
    private Button btnLimpar;

    @FXML
    private TableColumn<?, ?> colCPF;

    @FXML
    private TableColumn<?, ?> colEndereco;

    @FXML
    private TableColumn<?, ?> colEndereco1;

    @FXML
    private TableColumn<?, ?> colEndereco11;

    @FXML
    private TableColumn<?, ?> colHorarios;

    @FXML
    private TableColumn<?, ?> colMedico;

    @FXML
    private TableColumn<?, ?> colNome;

    @FXML
    private ComboBox<?> comboBoxHorarios;

    @FXML
    private ComboBox<?> comboBoxMedicos;

    @FXML
    private Text id;

    @FXML
    private Button secondaryButton;

    @FXML
    private TableView<Paciente> tableViewPacientes;

    @FXML
    private TextField txtCPF;

    @FXML
    private TextField txtEnd;

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtNasc;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtTel;

    @FXML
    void limparCampos(ActionEvent event) {

    }

    @FXML
    void switchToPrimary(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
