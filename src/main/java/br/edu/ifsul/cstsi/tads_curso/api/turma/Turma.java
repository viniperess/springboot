package br.edu.ifsul.cstsi.tads_curso.api.turma;

import br.edu.ifsul.cstsi.tads_curso.api.curso.Curso;
import br.edu.ifsul.cstsi.tads_curso.api.matricula.Matricula;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.Collection;

@Entity(name = "Turma")
@Table(name = "turmas")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Turma {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String turno;
    private LocalDate dt_ini;
    private LocalDate dt_fim;
    private Integer hr_ini;
    private Integer hr_fim;
    private Integer qtd_vagas;

    @OneToMany(mappedBy = "turma", fetch = FetchType.EAGER)
    private Collection<Matricula> matriculas;

    @ManyToOne
    @JoinColumn(name = "curso_id", referencedColumnName = "id", nullable = false)
    private Curso curso;

    public static Turma create(TurmaDTOResponse t){
        var modelMapper = new ModelMapper();
        return modelMapper.map(t, Turma.class);
    }
}
