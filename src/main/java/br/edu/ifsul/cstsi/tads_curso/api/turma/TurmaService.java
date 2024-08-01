package br.edu.ifsul.cstsi.tads_curso.api.turma;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class TurmaService {

    @Autowired
    private TurmaRepository rep;

    public Page<Turma> getTurmas(Pageable paginacao) { return rep.findAll(paginacao); }
    public Optional<Turma> getTurmaById(Long id) { return rep.findById(id); }

    public Turma insert(Turma turma) {
        Assert.isNull(turma.getId(), "Não foi possivel inserir o registro");
        return rep.save(turma);
    }

    public Turma update(Turma turma){
        var optional = rep.findById(turma.getId());
        if(optional.isPresent()){
            var db = optional.get();
            db.setTurno(turma.getTurno());
            db.setDt_ini(turma.getDt_ini());
            db.setDt_fim(turma.getDt_fim());
            db.setHr_ini(turma.getHr_ini());
            db.setHr_fim(turma.getHr_fim());
            db.setQtd_vagas(turma.getQtd_vagas());
            return rep.save(db);
        }
        return null;
    }

    public boolean delete(Long id){
        var optional = rep.findById(id);
        if (optional.isPresent()){
            rep.deleteById(id);
            return true;
        }
        return false;
    }
}
