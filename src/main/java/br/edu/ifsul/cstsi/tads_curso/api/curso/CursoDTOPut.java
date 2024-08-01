package br.edu.ifsul.cstsi.tads_curso.api.curso;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CursoDTOPut(
        @NotBlank(message = "O nome não pode ser nulo ou vazio")
        @Size(min = 2, max = 50, message = "Tamanho mínimo de 2 e máximo de 200")
        String nome,
        @NotBlank(message = "O pre requisito não pode ser nulo ou vazio")
        @Size(min = 2, max = 255, message = "Tamanho mínimo de 2 e máximo de 255")
        String pre_requisito,
        @NotNull
        @Min(0)
        Integer carga_hora,
        @NotNull
        @Min(0)
        BigDecimal valor
) {
    public CursoDTOPut(Curso curso){
        this(curso.getNome(), curso.getPre_requisito(), curso.getCarga_hora(), curso.getValor());
    }
}
