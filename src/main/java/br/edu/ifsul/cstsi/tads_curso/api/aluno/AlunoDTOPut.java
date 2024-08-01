package br.edu.ifsul.cstsi.tads_curso.api.aluno;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AlunoDTOPut(
        @NotBlank(message = "O nome não pode ser nulo ou vazio")
        @Size(min = 2, max = 50, message = "Tamanho mínimo de 2 e máximo de 200")
        String nome,
        @NotBlank(message = "O cpf não pode ser nulo ou vazio")
        @Size(max = 11, message = "Tamanho máximo de 11 caracter")
        String cpf,
        @NotBlank(message = "O rg não pode ser nulo ou vazio")
        @Size(max = 10, message = "Tamanho máximo de 10 caracter")
        String rg,
        @NotBlank(message = "O endereco não pode ser nulo ou vazio")
        @Size(min = 2, max = 50, message = "Tamanho mínimo de 2 e máximo de 200")
        String endereco,
        @NotBlank(message = "O fone não pode ser nulo ou vazio")
        @Size(max = 12, message = "Tamanho máximo de 12 caracter")
        String fone
) {
    public AlunoDTOPut(Aluno aluno) {
        this(aluno.getNome(), aluno.getCpf(), aluno.getRg(), aluno.getEndereco(), aluno.getFone());
    }
}
