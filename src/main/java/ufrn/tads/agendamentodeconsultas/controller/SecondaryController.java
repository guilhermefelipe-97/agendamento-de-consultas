package ufrn.tads.agendamentodeconsultas.controller;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import ufrn.tads.agendamentodeconsultas.App;
import ufrn.tads.agendamentodeconsultas.model.Paciente;
import javafx.scene.control.*;
import ufrn.tads.agendamentodeconsultas.service.PacienteService;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

public class SecondaryController {
    private PacienteService pacienteService = new PacienteService();
    private ObservableList<Paciente> listaPacientes;
    private int id = 0;

    @FXML private TableView<Paciente> tableViewPacientes;
    @FXML private TableColumn<Paciente, Integer> colID;
    @FXML private TableColumn<Paciente, String> colNome, colCPF, colDataNascimento, colTelefone, colEndereco, colMedico, colHorario, colData;
    @FXML private TextField txtNome, txtCPF, txtNasc, txtTel, txtEnd, txtBuscaNome, txtBuscaCPF, txtBuscaMedico;
    @FXML private ComboBox<String> comboBoxMedicos, comboBoxHorarios;
    @FXML private DatePicker dataPicker;
    @FXML private Button btnCadastrar;

    @FXML
    public void initialize() {
        // Preencher os ComboBoxs
        comboBoxMedicos.getItems().addAll(
                "Dr. João - Cardiologista",
                "Dra. Maria - Dermatologista",
                "Dr. Carlos - Ortopedista",
                "Dra. Ana - Pediatra",
                "Dr. Pedro - Neurologista",
                "Dra. Clara - Ginecologista"
        );

        comboBoxHorarios.getItems().addAll(
                "8:00", "8:30", "9:00", "9:30", "10:00",
                "10:30", "11:00", "11:30", "12:00"
        );

        // Inicializa a lista de pacientes
        listaPacientes = pacienteService.obterPacientes();
        tableViewPacientes.setItems(listaPacientes);
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colDataNascimento.setCellValueFactory(new PropertyValueFactory<>("dataNascimento"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        colMedico.setCellValueFactory(new PropertyValueFactory<>("medico"));
        colHorario.setCellValueFactory(new PropertyValueFactory<>("horario"));
        colData.setCellValueFactory(new PropertyValueFactory<>("data"));
    }

    @FXML
    void cadastrarPaciente(ActionEvent event) {
        pacienteService.adicionarPaciente(txtNome.getText(), txtCPF.getText(), txtNasc.getText(), txtTel.getText(), txtEnd.getText(), comboBoxMedicos.getValue(), comboBoxHorarios.getValue(), dataPicker.getValue());
        atualizarLista();
    }

    @FXML
    void excluirCadastro(ActionEvent event) {
        // Metodo para excluir, com a confirmação do usuário
        // Criar o alerta de confirmação
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Exclusão");
        alert.setHeaderText("Você tem certeza que deseja excluir?");
        alert.setContentText("Essa ação não pode ser desfeita.");

        // Criando os botões personalizados
        ButtonType buttonConfirmar = new ButtonType("Confirmar");
        ButtonType buttonCancelar = new ButtonType("Cancelar");

        // Definindo os botões do alerta
        alert.getButtonTypes().setAll(buttonConfirmar, buttonCancelar);

        // Exibir o alerta e capturar a resposta do usuário
        ButtonType resposta = alert.showAndWait().orElse(buttonCancelar);  // Caso o usuário feche o alerta, assume "Cancelar"

        // Verificação da resposta
        if (resposta == buttonConfirmar) {
            pacienteService.removerPaciente(id);
            System.out.println("Exclusão realizada com sucesso.");
        } else {
            System.out.println("Exclusão cancelada.");
        }
        atualizarLista();
    }

    @FXML
    void alterarCadastro(ActionEvent event) {
        // Passando os dados dos campos para o metodo atualizarPaciente
        pacienteService.atualizarPaciente(
                id,
                txtNome.getText(),
                txtCPF.getText(),
                txtNasc.getText(),
                txtTel.getText(),
                txtEnd.getText(),
                comboBoxMedicos.getValue(),
                comboBoxHorarios.getValue(),
                dataPicker.getValue()
        );
        // Atualiza a lista de pacientes após a alteração
        atualizarLista();
    }

    @FXML
    private void atualizarLista() {
        listaPacientes.setAll(pacienteService.obterPacientes());
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

    @FXML
    void buscarPacientes(ActionEvent event) {
        // Criando uma lista filtrada com a lista de pacientes
        FilteredList<Paciente> listaFiltrada = new FilteredList<>(listaPacientes, p -> true);

        listaFiltrada.setPredicate(paciente -> {
            // Pegando os valores digitados nos campos de busca
            String nomeFiltro = txtBuscaNome.getText() != null ? txtBuscaNome.getText().toLowerCase() : "";
            String cpfFiltro = txtBuscaCPF.getText() != null ? txtBuscaCPF.getText().toLowerCase() : "";
            String medicoFiltro = txtBuscaMedico.getText() != null ? txtBuscaMedico.getText().toLowerCase() : "";

            // Verifica se os campos estão vazios e retorna todos os resultados
            if (nomeFiltro.isBlank() && cpfFiltro.isBlank() && medicoFiltro.isBlank()) {
                return true; // Não há filtros, exibe todos os pacientes
            }

            // Verifica se o paciente corresponde a algum dos filtros
            boolean nomeMatch = paciente.getNome().toLowerCase().contains(nomeFiltro);
            boolean cpfMatch = paciente.getCpf().toLowerCase().contains(cpfFiltro);
            boolean medicoMatch = paciente.getMedico().toLowerCase().contains(medicoFiltro);

            // Retorna true apenas se o paciente atender a todos os filtros preenchidos
            return nomeMatch && cpfMatch && medicoMatch;
        });

        // Atualiza a TableView com os resultados filtrados
        tableViewPacientes.setItems(listaFiltrada);
        tableViewPacientes.refresh(); // Força a atualização da tabela
    }

    @FXML
    void switchToPrimary(ActionEvent event) throws Exception {
        // Criando um alerta de confirmação com botões personalizados
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Logoff");
        alert.setHeaderText("Deseja realmente deslogar?");
        alert.setContentText("Você retornará para a tela de Login.");

        // Criando botões personalizados
        ButtonType buttonSim = new ButtonType("Sim");
        ButtonType buttonNao = new ButtonType("Não");

        // Definindo os botões do alerta
        alert.getButtonTypes().setAll(buttonSim, buttonNao);

        // Exibir o alerta e capturar a resposta do usuário
        ButtonType resposta = alert.showAndWait().orElse(buttonNao);  // Caso o usuário feche o alerta, assume "Não"

        // Verificação da resposta
        if (resposta == buttonSim) {
            App.setRoot("PrimaryController");
            System.out.println("Logoff realizado com sucesso.");
        }
    }

    @FXML
    void switchToTertiary(ActionEvent event) throws Exception {
        App.setRoot("TertiaryController");
    }
}
