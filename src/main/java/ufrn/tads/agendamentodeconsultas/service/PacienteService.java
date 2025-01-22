package ufrn.tads.agendamentodeconsultas.service;

import ufrn.tads.agendamentodeconsultas.repository.PacienteDao;
import ufrn.tads.agendamentodeconsultas.model.Paciente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PacienteService {

    private PacienteDao pacienteDao = new PacienteDao();

    public ObservableList<Paciente> obterPacientes() {
        List<Paciente> lista = pacienteDao.listarPacientes();
        return FXCollections.observableArrayList(lista);
    }

    public void adicionarPaciente(String nome, String cpf, String nascimento, String telefone, String endereco, String medico, String horario, LocalDate data) {
        Paciente paciente = new Paciente();
        paciente.setNome(nome);
        paciente.setCpf(cpf);
        paciente.setDataNascimento(nascimento);
        paciente.setTelefone(telefone);
        paciente.setEndereco(endereco);
        paciente.setMedico(medico);
        paciente.setHorario(horario);
        paciente.setData(data.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        pacienteDao.cadastrarPaciente(paciente);
    }

    public void atualizarPaciente(int id, String nome, String cpf, String nascimento, String telefone, String endereco, String medico, String horario, LocalDate data) {
        Paciente paciente = new Paciente();
        paciente.setId(id);
        paciente.setNome(nome);
        paciente.setCpf(cpf);
        paciente.setDataNascimento(nascimento);
        paciente.setTelefone(telefone);
        paciente.setEndereco(endereco);
        paciente.setMedico(medico);
        paciente.setHorario(horario);
        paciente.setData(data.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        pacienteDao.alterarPaciente(paciente);
    }

    public void removerPaciente(int id) {
        pacienteDao.excluirPaciente(id);
    }
}

