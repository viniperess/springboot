package br.edu.ifsul.cstsi.tads_curso.api.aluno;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    @Query(value = "SELECT a FROM Aluno a where a.nome like ?1 order by a.nome")
    List<Aluno> findByNome(String nome);
}
