package ufrn.tads.agendamentodeconsultas.model;

import java.time.LocalDate;

public class Paciente {
    private int id;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String telefone;
    private String endereco;

    public Paciente (int id, String nome, String cpf, LocalDate dataNascimento,String telefone) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
    }
}
