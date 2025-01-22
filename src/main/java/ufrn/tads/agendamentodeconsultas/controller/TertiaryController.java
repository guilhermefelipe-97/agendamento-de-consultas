package ufrn.tads.agendamentodeconsultas.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import ufrn.tads.agendamentodeconsultas.App;
import ufrn.tads.agendamentodeconsultas.model.Paciente;
import ufrn.tads.agendamentodeconsultas.repository.DBConnection;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TertiaryController implements Initializable {
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;

    @FXML
    private Button btnBuscar;
    private ObservableList<Paciente> listaPacientes;

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
    private TableColumn<Paciente, String> colCPF;

    @FXML
    private TableColumn<Paciente, String> colDataNascimento;

    @FXML
    private TableColumn<Paciente, String> colEndereco;

    @FXML
    private TableColumn<Paciente, String> colNome;

    @FXML
    private TableColumn<Paciente, String> colTelefone;

    @FXML
    private TextField txtBuscaCPF;

    @FXML
    private TextField txtBuscaNascimento;

    @FXML
    private TextField txtBuscaNome;

    @FXML
    private TableView<Paciente> tableViewPacientes;
    int id = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listaPacientes = getPacientes();
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
        listaPacientes = getPacientes();
        tableViewPacientes.setItems(listaPacientes);
        colCPF.setCellValueFactory(new PropertyValueFactory<Paciente, String>("cpf"));
        colEndereco.setCellValueFactory(new PropertyValueFactory<Paciente, String>("endereco"));
        colDataNascimento.setCellValueFactory(new PropertyValueFactory<Paciente, String>("dataNascimento"));
        colNome.setCellValueFactory(new PropertyValueFactory<Paciente, String>("nome"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<Paciente, String>("telefone"));
    }

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
        // Metodo para excluir, com a confirmação do usuário
        // Criar o alerta de confirmação
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Exclusão");
        alert.setHeaderText("Você tem certeza que deseja excluir?");
        alert.setContentText("Essa ação não pode ser desfeita.");
        // Exibir o alerta e capturar a resposta do usuário
        ButtonType resposta = alert.showAndWait().orElse(ButtonType.CANCEL);
        // Verificação da resposta
        if (resposta == ButtonType.OK) {
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
            System.out.println("Item excluído!");
        } else {
            System.out.println("Exclusão cancelada.");
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
    void buscarPacientes(ActionEvent event) {
        // Criando uma lista filtrada com a lista de pacientes
        FilteredList<Paciente> listaFiltrada = new FilteredList<>(listaPacientes, p -> true);

        listaFiltrada.setPredicate(paciente -> {
            // Pegando os valores digitados nos campos de busca (evitando null)
            String nomeFiltro = txtBuscaNome.getText() != null ? txtBuscaNome.getText().toLowerCase() : "";
            String cpfFiltro = txtBuscaCPF.getText() != null ? txtBuscaCPF.getText().toLowerCase() : "";
            String nascimentoFiltro = txtBuscaNascimento.getText() != null ? txtBuscaNascimento.getText().toLowerCase() : "";

            // Verifica se os campos estão vazios e retorna todos os resultados
            if (nomeFiltro.isBlank() && cpfFiltro.isBlank() && nascimentoFiltro.isBlank()) {
                return true;
            }

            // Verifica se o paciente corresponde a algum dos filtros
            boolean nomeMatch = paciente.getNome().toLowerCase().contains(nomeFiltro) || nomeFiltro.isBlank();
            boolean cpfMatch = paciente.getCpf().toLowerCase().contains(cpfFiltro) || cpfFiltro.isBlank();
            boolean nascimentoMatch = paciente.getDataNascimento().toLowerCase().contains(nascimentoFiltro) || nascimentoFiltro.isBlank();

            // Retorna true apenas se o paciente atender a todos os filtros preenchidos
            return nomeMatch && cpfMatch && nascimentoMatch;
        });

        // Atualiza a TableView com os resultados filtrados
        tableViewPacientes.setItems(listaFiltrada);
        tableViewPacientes.refresh(); // Força a atualização da tabela
    }

    @FXML
    void switchToPrimary(ActionEvent event) throws Exception{
        App.setRoot("PrimaryController");
    }

    @FXML
    private void switchToSecondaryButton(ActionEvent event) throws Exception {
        App.setRoot("SecondaryController");
    }
}
