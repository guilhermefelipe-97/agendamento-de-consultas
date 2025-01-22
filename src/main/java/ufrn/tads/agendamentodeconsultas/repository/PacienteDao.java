package ufrn.tads.agendamentodeconsultas.repository;

import ufrn.tads.agendamentodeconsultas.model.Paciente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteDao {
    private Connection con;
    private PreparedStatement st;
    private ResultSet rs;

    public List<Paciente> listarPacientes() {
        List<Paciente> pacientes = new ArrayList<>();
        String query = "SELECT * FROM pacientes";
        con = DBConnection.conectar();
        try {
            st = con.prepareStatement(query);
            rs = st.executeQuery();
            while (rs.next()) {
                Paciente paciente = new Paciente();
                paciente.setId(rs.getInt("id"));
                paciente.setNome(rs.getString("nome"));
                paciente.setCpf(rs.getString("cpf"));
                paciente.setDataNascimento(rs.getString("nascimento"));
                paciente.setTelefone(rs.getString("telefone"));
                paciente.setEndereco(rs.getString("endereco"));
                paciente.setMedico(rs.getString("medico"));
                paciente.setHorario(rs.getString("horario"));
                paciente.setData(rs.getString("data"));
                pacientes.add(paciente);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return pacientes;
    }

    public void cadastrarPaciente(Paciente paciente) {
        String insert = "INSERT INTO pacientes (nome,cpf,nascimento,telefone,endereco,medico,horario,data) VALUES (?,?,?,?,?,?,?,?)";
        con = DBConnection.conectar();
        try {
            st = con.prepareStatement(insert);
            st.setString(1, paciente.getNome());
            st.setString(2, paciente.getCpf());
            st.setString(3, paciente.getDataNascimento());
            st.setString(4, paciente.getTelefone());
            st.setString(5, paciente.getEndereco());
            st.setString(6, paciente.getMedico());
            st.setString(7, paciente.getHorario());
            st.setString(8, paciente.getData());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void alterarPaciente(Paciente paciente) {
        String update = "UPDATE pacientes SET nome=?, cpf=?, nascimento=?, telefone=?, endereco=?, medico=?, horario=?, data=? WHERE id=?";
        con = DBConnection.conectar();
        try {
            st = con.prepareStatement(update);
            st.setString(1, paciente.getNome());
            st.setString(2, paciente.getCpf());
            st.setString(3, paciente.getDataNascimento());
            st.setString(4, paciente.getTelefone());
            st.setString(5, paciente.getEndereco());
            st.setString(6, paciente.getMedico());
            st.setString(7, paciente.getHorario());
            st.setString(8, paciente.getData());
            st.setInt(9, paciente.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void excluirPaciente(int id) {
        String delete = "DELETE FROM pacientes WHERE id=?";
        con = DBConnection.conectar();
        try {
            st = con.prepareStatement(delete);
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Item excluído!");
    }
}