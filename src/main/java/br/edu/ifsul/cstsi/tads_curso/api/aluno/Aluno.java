package br.edu.ifsul.cstsi.tads_curso.api.aluno;

import br.edu.ifsul.cstsi.tads_curso.api.matricula.Matricula;
import jakarta.persistence.*;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.util.Collection;

@Entity(name = "Aluno")
@Table(name = "alunos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Aluno {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String fone;
    private String endereco;
    private String cpf;
    private String rg;

    @OneToMany(mappedBy = "aluno", fetch = FetchType.EAGER)
    private Collection<Matricula> matriculas;

    public static Aluno create(AlunoDTOResponse a){
        var modelMapper = new ModelMapper();
        return modelMapper.map(a, Aluno.class);
    }
}
