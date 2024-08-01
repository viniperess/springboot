package br.edu.ifsul.cstsi.tads_curso.api.matricula;

import br.edu.ifsul.cstsi.tads_curso.api.aluno.Aluno;
import br.edu.ifsul.cstsi.tads_curso.api.turma.Turma;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;

@Entity(name = "Matricula")
@Table(name = "matriculas")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Matricula {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate data;
    private String forma_pagto;
    private Integer num_mat;

    @ManyToOne
    @JoinColumn(name = "aluno_id", referencedColumnName = "id", nullable = false)
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "turma_id", referencedColumnName = "id", nullable = false)
    private Turma turma;

    public static Matricula create(MatriculaDTOResponse m){
        var modelMapper = new ModelMapper();
        return modelMapper.map(m, Matricula.class);
    }
}
