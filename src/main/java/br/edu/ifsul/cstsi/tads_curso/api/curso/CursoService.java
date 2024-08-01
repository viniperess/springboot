package br.edu.ifsul.cstsi.tads_curso.api.curso;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class CursoService {

    @Autowired
    private CursoRepository rep;

    public Page<Curso> getCursos(Pageable paginacao) {
        return rep.findAll(paginacao);
    }

    public Optional<Curso> getCursoById(Long id) {
        return rep.findById(id);
    }

    public List<Curso> getCursosByNome(String nome) {
        return rep.findByNome(nome+"%");
    }

    public Curso insert(Curso curso) {
        Assert.isNull(curso.getId(),"Não foi possível inserir o registro");
        return rep.save(curso);
    }

    public Curso update(Curso curso) {
        Assert.notNull(curso.getId(),"Não foi possível atualizar o registro");


        var optional = rep.findById(curso.getId());
        if(optional.isPresent()) {
            var db = optional.get();
            db.setNome(curso.getNome());
            db.setPre_requisito(curso.getPre_requisito());
            db.setCarga_hora(curso.getCarga_hora());
            db.setValor(curso.getValor());
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
