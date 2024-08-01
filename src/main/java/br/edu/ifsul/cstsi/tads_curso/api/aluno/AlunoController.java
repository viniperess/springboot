package br.edu.ifsul.cstsi.tads_curso.api.aluno;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController //indica que essa classe deve ser adicionada ao Contexto do aplicativo como um Bean da camada de controle API REST
@RequestMapping("api/v1/alunos")
public class AlunoController {

    @Autowired
    private AlunoService service;

    @GetMapping
    public ResponseEntity<Page<AlunoDTOResponse>> selectAll(@PageableDefault(size = 50, sort = "nome") Pageable paginacao){
        return ResponseEntity.ok(service.getAlunos(paginacao).map(AlunoDTOResponse::new));
    }

    @GetMapping("{id}")
    public ResponseEntity<AlunoDTOResponse> selectById(@PathVariable("id") Long id) {
        var a = service.getAlunoById(id);
        if(a.isPresent()){
            return ResponseEntity.ok(new AlunoDTOResponse(a.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<AlunoDTOResponse>> selectByNome(@PathVariable("nome") String nome) {
        var alunos = service.getAlunosByNome(nome);
        return alunos.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(alunos.stream().map(AlunoDTOResponse::new).collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<URI> insert(@RequestBody AlunoDTOPost alunoDTOPost, UriComponentsBuilder uriBuilder){
        var a = service.insert(new Aluno(
                null,
                alunoDTOPost.nome(),
                alunoDTOPost.cpf(),
                alunoDTOPost.rg(),
                alunoDTOPost.endereco(),
                alunoDTOPost.fone(),
                null
        ));
        var location = uriBuilder.path("api/v1/alunos/{id}").buildAndExpand(a.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<AlunoDTOResponse> update(@PathVariable("id") Long id, @Valid @RequestBody AlunoDTOPut alunoDTOPut){
        var a = service.update(new Aluno(
                id,
                alunoDTOPut.nome(),
                alunoDTOPut.cpf(),
                alunoDTOPut.rg(),
                alunoDTOPut.endereco(),
                alunoDTOPut.fone(),
                null
        ));
        return a != null ?
                ResponseEntity.ok(new AlunoDTOResponse(a)):
                ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable("id") Long id){
        return service.delete(id) ?
                ResponseEntity.ok().build() :
                ResponseEntity.notFound().build();
    }


}
