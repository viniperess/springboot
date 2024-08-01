package br.edu.ifsul.cstsi.tads_curso.api.matricula;

import java.time.LocalDate;

public record MatriculaDTOResponse(
        Long id,
        LocalDate data,
        String forma_pagto,
        Integer num_mat
) {
    public MatriculaDTOResponse(Matricula matricula){
        this(matricula.getId(), matricula.getData(), matricula.getForma_pagto(), matricula.getNum_mat());
    }
}
