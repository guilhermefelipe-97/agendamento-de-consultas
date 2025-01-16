package ufrn.tads.agendamentodeconsultas.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import ufrn.tads.agendamentodeconsultas.App;
import ufrn.tads.agendamentodeconsultas.model.Paciente;
import ufrn.tads.agendamentodeconsultas.repository.DBConnection;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SecondaryController implements Initializable {
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;

    @FXML
    private Button btnAlterar;

    @FXML
    private Button btnCadastrar;

    @FXML
    private Button btnExcluir;

    @FXML
    private Button btnLimpar;

    @FXML
    private TableColumn<Paciente, String> colCPF;

    @FXML
    private TableColumn<Paciente, String> colDataNascimento;

    @FXML
    private TableColumn<Paciente, String> colEndereco;

    @FXML
    private TableColumn<Paciente, String> colHorario;

    @FXML
    private TableColumn<Paciente, Integer> colID;

    @FXML
    private TableColumn<Paciente, String> colMedico;

    @FXML
    private TableColumn<Paciente, String> colNome;

    @FXML
    private TableColumn<Paciente, String> colTelefone;

    @FXML
    private ComboBox<?> comboBoxHorarios;

    @FXML
    private ComboBox<?> comboBoxMedicos;

    @FXML
    private Button secondaryButton;

    @FXML
    private TableView<Paciente> tableViewPacientes;
    int id = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mostrarPacientes();
    }

    public ObservableList<Paciente> getPacientes(){
        ObservableList<Paciente> pacientes = FXCollections.observableArrayList();

        String query = "select * from pacientes";
        con = DBConnection.conectar();
        try {
            st = con.prepareStatement(query);
            rs = st.executeQuery();
            while (rs.next()){
                Paciente st = new Paciente();
                st.setId(rs.getInt("id"));
                st.setNome(rs.getString("nome"));
                st.setCpf(rs.getString("cpf"));
                st.setDataNascimento(rs.getString("nascimento"));
                st.setTelefone(rs.getString("telefone"));
                st.setEndereco(rs.getString("endereco"));
                pacientes.add(st);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return pacientes;
    }

    public void mostrarPacientes(){
        ObservableList<Paciente> list = getPacientes();
        tableViewPacientes.setItems(list);
        colID.setCellValueFactory(new PropertyValueFactory<Paciente, Integer>("id"));
        colCPF.setCellValueFactory(new PropertyValueFactory<Paciente, String>("cpf"));
        colEndereco.setCellValueFactory(new PropertyValueFactory<Paciente, String>("endereco"));
        colDataNascimento.setCellValueFactory(new PropertyValueFactory<Paciente, String>("dataNascimento"));
        colNome.setCellValueFactory(new PropertyValueFactory<Paciente, String>("nome"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<Paciente, String>("telefone"));
    }

    @FXML
    private TextField txtCPF;

    @FXML
    private TextField txtEnd;

    @FXML
    private TextField txtNasc;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtTel;

    @FXML
    void cadastrarPaciente(ActionEvent event) {
        String insert = "insert into pacientes (nome,cpf,nascimento,telefone,endereco) values (?,?,?,?,?)";
        con = DBConnection.conectar();
        try {
            st = con.prepareStatement(insert);
                st.setString(1,txtNome.getText());
                st.setString(2,txtCPF.getText());
                st.setString(3,txtNasc.getText());
                st.setString(4,txtTel.getText());
                st.setString(5,txtEnd.getText());
                st.executeUpdate();
                clear();
                mostrarPacientes();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void alterarCadastro(ActionEvent event) {
        String update = "UPDATE pacientes SET nome = ?, cpf = ?, nascimento = ?, telefone = ?, endereco = ? WHERE id = ?";
        con = DBConnection.conectar();
        try {
            st = con.prepareStatement(update);
            st.setString(1,txtNome.getText());
            st.setString(2,txtCPF.getText());
            st.setString(3,txtNasc.getText());
            st.setString(4,txtTel.getText());
            st.setString(5,txtEnd.getText());
            st.setInt(6,id);
            st.executeUpdate();
            mostrarPacientes();
            clear();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void excluirCadastro(ActionEvent event) {
        String delete = "DELETE FROM pacientes WHERE id = ?";
        con = DBConnection.conectar();
        try {
            st = con.prepareStatement(delete);
            st.setInt(1,id);
            st.executeUpdate();
            mostrarPacientes();
            clear();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void getData(MouseEvent event) {
        Paciente paciente = tableViewPacientes.getSelectionModel().getSelectedItem();
        id = paciente.getId();
        txtNome.setText(paciente.getNome());
        txtCPF.setText(paciente.getCpf());
        txtNasc.setText(paciente.getDataNascimento());
        txtTel.setText(paciente.getTelefone());
        txtEnd.setText(paciente.getEndereco());
        btnCadastrar.setDisable(true);
    }

    void clear(){
        txtNome.setText(null);
        txtTel.setText(null);
        txtNasc.setText(null);
        txtCPF.setText(null);
        txtEnd.setText(null);
        btnCadastrar.setDisable(false);
    }

    @FXML
    void limparCampos(ActionEvent event) {
        clear();
    }

    @FXML
    void switchToPrimary(ActionEvent event) throws Exception {
        App.setRoot("PrimaryController");
    }
}
