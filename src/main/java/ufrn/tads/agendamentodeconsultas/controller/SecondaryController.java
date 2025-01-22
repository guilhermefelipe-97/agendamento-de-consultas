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
import javafx.scene.control.DatePicker;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class SecondaryController implements Initializable {
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;

    @FXML
    private Button btnBuscar;
    private ObservableList<Paciente> listaPacientes;

    @FXML
    private Button btnAlterar;

    @FXML
    private Button btnCadastrar;

    @FXML
    private Button btnExcluir;

    @FXML
    private Button btnLimpar;

    @FXML
    private DatePicker dataPicker;

    @FXML
    private TableColumn<Paciente, String> colData;

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
    private ComboBox<String> comboBoxHorarios;

    @FXML
    private ComboBox<String> comboBoxMedicos;

    @FXML
    private Button secondaryButton;

    @FXML
    private TableView<Paciente> tableViewPacientes;
    int id = 0;

    @FXML
    private TextField txtBuscaCPF;

    @FXML
    private TextField txtBuscaMedico;

    @FXML
    private TextField txtBuscaNome;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listaPacientes = getPacientes();
        mostrarPacientes();
        comboBoxMedicos.getItems().addAll(
                "Dr. João - Cardiologista",
                "Dra. Maria - Dermatologista",
                "Dr. Carlos - Ortopedista",
                "Dra. Ana - Pediatra",
                "Dr. Pedro - Neurologista",
                "Dra. Clara - Ginecologista"
        );


        comboBoxHorarios.getItems().addAll(
                "8:00",
                "8:30",
                "9:00",
                "9:30",
                "10:00",
                "10:30",
                "11:00",
                "11:30",
                "12:00"
        );
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
                st.setMedico(rs.getString("medico"));
                st.setHorario(rs.getString("horario"));
                st.setData(rs.getString("data"));
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
        colID.setCellValueFactory(new PropertyValueFactory<Paciente, Integer>("id"));
        colCPF.setCellValueFactory(new PropertyValueFactory<Paciente, String>("cpf"));
        colEndereco.setCellValueFactory(new PropertyValueFactory<Paciente, String>("endereco"));
        colDataNascimento.setCellValueFactory(new PropertyValueFactory<Paciente, String>("dataNascimento"));
        colNome.setCellValueFactory(new PropertyValueFactory<Paciente, String>("nome"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<Paciente, String>("telefone"));
        colMedico.setCellValueFactory(new PropertyValueFactory<Paciente, String>("medico"));
        colHorario.setCellValueFactory(new PropertyValueFactory<Paciente, String>("horario"));
        colData.setCellValueFactory(new PropertyValueFactory<Paciente, String>("data"));
    }

    @FXML
    void cadastrarPaciente(ActionEvent event) {
        String insert = "insert into pacientes (nome,cpf,nascimento,telefone,endereco,medico,horario,data) values (?,?,?,?,?,?,?,?)";
        con = DBConnection.conectar();
        try {
            st = con.prepareStatement(insert);
                st.setString(1,txtNome.getText());
                st.setString(2,txtCPF.getText());
                st.setString(3,txtNasc.getText());
                st.setString(4,txtTel.getText());
                st.setString(5,txtEnd.getText());
                st.setString(6,comboBoxMedicos.getValue());
                st.setString(7,comboBoxHorarios.getValue());
                LocalDate dataSelecionada = dataPicker.getValue();
                String dataSelecionadaFormatada = dataSelecionada.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                st.setString(8, dataSelecionadaFormatada);
                st.executeUpdate();
                clear();
                mostrarPacientes();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void alterarCadastro(ActionEvent event) {
        String update = "UPDATE pacientes SET nome = ?, cpf = ?, nascimento = ?, telefone = ?, endereco = ?, medico = ?, horario = ?, data = ? WHERE id = ?";
        con = DBConnection.conectar();
        try {
            st = con.prepareStatement(update);
            st.setString(1,txtNome.getText());
            st.setString(2,txtCPF.getText());
            st.setString(3,txtNasc.getText());
            st.setString(4,txtTel.getText());
            st.setString(5,txtEnd.getText());
            st.setString(6,comboBoxMedicos.getValue());
            st.setString(7,comboBoxHorarios.getValue());
            LocalDate dataSelecionada = dataPicker.getValue();
            String dataSelecionadaFormatada = dataSelecionada.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            st.setString(8, dataSelecionadaFormatada);
            st.setInt(9,id);
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
        comboBoxMedicos.setValue(paciente.getMedico());
        comboBoxHorarios.setValue(paciente.getHorario());
        dataPicker.setValue(LocalDate.parse(paciente.getData(), DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        btnCadastrar.setDisable(true);
    }

    void clear(){
        txtNome.setText(null);
        txtTel.setText(null);
        txtNasc.setText(null);
        txtCPF.setText(null);
        txtEnd.setText(null);
        comboBoxMedicos.setValue(null);
        comboBoxHorarios.setValue(null);
        dataPicker.setValue(null);
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
            String medicoFiltro = txtBuscaMedico.getText() != null ? txtBuscaMedico.getText().toLowerCase() : "";

            // Verifica se os campos estão vazios e retorna todos os resultados
            if (nomeFiltro.isBlank() && cpfFiltro.isBlank() && medicoFiltro.isBlank()) {
                return true;
            }

            // Verifica se o paciente corresponde a algum dos filtros
            boolean nomeMatch = paciente.getNome().toLowerCase().contains(nomeFiltro) || nomeFiltro.isBlank();
            boolean cpfMatch = paciente.getCpf().toLowerCase().contains(cpfFiltro) || cpfFiltro.isBlank();
            boolean medicoMatch = paciente.getMedico().toLowerCase().contains(medicoFiltro) || medicoFiltro.isBlank();

            // Retorna true apenas se o paciente atender a todos os filtros preenchidos
            return nomeMatch && cpfMatch && medicoMatch;
        });

        // Atualiza a TableView com os resultados filtrados
        tableViewPacientes.setItems(listaFiltrada);
        tableViewPacientes.refresh(); // Força a atualização da tabela
    }

    @FXML
    void switchToPrimary(ActionEvent event) throws Exception {
        App.setRoot("PrimaryController");
    }

    @FXML
    void switchToTertiary(ActionEvent event) throws Exception {
        App.setRoot("TertiaryController");
    }
}
