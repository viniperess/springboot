package br.edu.ifsul.cstsi.tads_curso.api.matricula;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class MatriculaService {

    @Autowired
    private MatriculaRepository rep;

    public Page<Matricula> getMatriculas(Pageable paginacao) { return rep.findAll(paginacao); }

    public Optional<Matricula> getMatriculaById(Long id) { return rep.findById(id); }

    public Matricula insert(Matricula matricula){
        Assert.isNull(matricula.getId(), "Não foi possivel inserir o registro");
        return rep.save(matricula);
    }

    public Matricula update(Matricula matricula){
        Assert.notNull(matricula.getId(), "Não foi possivel atualizar o registro");

        var optional = rep.findById(matricula.getId());
        if (optional.isPresent()) {
            var db = optional.get();
            db.setData(matricula.getData());
            db.setForma_pagto(matricula.getForma_pagto());
            db.setNum_mat(matricula.getNum_mat());
            return rep.save(db);
        }
        return null;
    }

    public boolean delete(Long id) {
        var optional = rep.findById(id);
        if (optional.isPresent()){
            rep.deleteById(id);
            return true;
        }
        return false;
    }
}
