package br.edu.ifsul.cstsi.tads_curso.api.curso;

import br.edu.ifsul.cstsi.tads_curso.api.turma.Turma;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Collection;

@Entity(name = "Curso")
@Table(name = "cursos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Curso {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String pre_requisito;
    private Integer carga_hora;
    private BigDecimal valor;

    @OneToMany(mappedBy = "curso", fetch = FetchType.EAGER)
    private Collection<Turma> turmas;

}
