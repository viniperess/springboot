package br.edu.ifsul.cstsi.tads_curso.api.turma;

import java.time.LocalDate;

public record TurmaDTOResponse(
        Long id,
        String turno,
        LocalDate dt_ini,
        LocalDate dt_fim,
        Integer hr_ini,
        Integer hr_fim,
        Integer qtd_vagas
) {
    public TurmaDTOResponse(Turma turma) {
        this(turma.getId(), turma.getTurno(), turma.getDt_ini(), turma.getDt_fim(), turma.getHr_ini(), turma.getHr_fim(), turma.getQtd_vagas());
    }
}