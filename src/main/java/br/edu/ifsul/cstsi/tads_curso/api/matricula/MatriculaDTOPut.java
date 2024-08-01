package br.edu.ifsul.cstsi.tads_curso.api.matricula;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record MatriculaDTOPut(
        @NotNull(message = "A data não pode ser nula")
        @FutureOrPresent(message = "A data deve ser no futuro ou no presente")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate data,

        @NotBlank(message = "A forma de pagamento não pode ser nula ou vazio")
        @Size(min = 2, max = 255, message = "Tamanho mínimo de 2 e máximo de 255")
        String forma_pagto,

        @NotNull
        @Min(0)
        Integer num_mat,

        @NotNull Long alunoId,
        @NotNull Long turmaId

) {
    public MatriculaDTOPut(Matricula matricula){
        this(matricula.getData(), matricula.getForma_pagto(), matricula.getNum_mat(), matricula.getAluno().getId(), matricula.getTurma().getId());
    }

}
