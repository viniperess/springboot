package br.edu.ifsul.cstsi.tads_curso.api.aluno;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository rep;

    public Page<Aluno> getAlunos(Pageable paginacao) {
        return rep.findAll(paginacao);
    }

    public Optional<Aluno> getAlunoById(Long id) {
        return rep.findById(id);
    }

    public List<Aluno> getAlunosByNome(String nome) {
        return rep.findByNome(nome+"%");
    }

    public Aluno insert(Aluno aluno) {
        Assert.isNull(aluno.getId(),"Não foi possível inserir o registro");
        return rep.save(aluno);
    }

    public Aluno update(Aluno aluno) {
        Assert.notNull(aluno.getId(),"Não foi possível atualizar o registro");


        var optional = rep.findById(aluno.getId());
        if(optional.isPresent()) {
            var db = optional.get();
            // Copia as propriedades
            db.setNome(aluno.getNome());
            db.setCpf(aluno.getCpf());
            db.setRg(aluno.getRg());
            db.setEndereco(aluno.getEndereco());
            db.setFone(aluno.getFone());
            return rep.save(db);
        }
        return null;
    }

    public boolean delete(Long id) {
        var optional = rep.findById(id);
        if(optional.isPresent()){
            rep.deleteById(id);
            return true;
        }
        return false;
    }
}
