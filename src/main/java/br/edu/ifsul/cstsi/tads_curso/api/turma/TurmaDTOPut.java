package br.edu.ifsul.cstsi.tads_curso.api.turma;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import org.aspectj.weaver.ast.Not;

import java.time.LocalDate;

public record TurmaDTOPut(
        @NotBlank(message = "O turno não pode ser nulo ou vazio") // Verifica se está vazio e estabelece como obrigatório (não pode ser nulo)
        @Size(min = 2, max = 50, message = "O turno deve ter entre 2 e 50 caracteres")
        String turno,

        @NotNull(message = "A data de início não pode ser nula")
        @FutureOrPresent(message = "A data de início deve ser no passado ou presente")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate dt_ini,

        @NotNull(message = "A data de fim não pode ser nula")
        @FutureOrPresent(message = "A data de fim deve ser no futuro ou presente")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate dt_fim,

        @NotNull(message = "A hora de início não pode ser nula")
        @Min(value = 0, message = "A hora de início deve ser um valor positivo ou zero")
        Integer hr_ini,

        @NotNull(message = "A hora de fim não pode ser nula")
        @Min(value = 0, message = "A hora de fim deve ser um valor positivo ou zero")
        Integer hr_fim,

        @NotNull(message = "A quantidade de vagas não pode ser nula")
        @Min(value = 0, message = "A quantidade de vagas deve ser um valor positivo ou zero")
        Integer qtd_vagas,

        @NotNull Long cursoId
) {
    public TurmaDTOPut(Turma turma){
        this(turma.getTurno(), turma.getDt_ini(), turma.getDt_fim(), turma.getHr_ini(), turma.getHr_fim(), turma.getQtd_vagas(), turma.getCurso().getId());
    }
}
