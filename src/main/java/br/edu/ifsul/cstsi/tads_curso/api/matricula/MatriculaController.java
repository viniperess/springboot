package br.edu.ifsul.cstsi.tads_curso.api.matricula;

import br.edu.ifsul.cstsi.tads_curso.api.aluno.Aluno;
import br.edu.ifsul.cstsi.tads_curso.api.aluno.AlunoRepository;
import br.edu.ifsul.cstsi.tads_curso.api.exceptions.ResourceNotFoundException;
import br.edu.ifsul.cstsi.tads_curso.api.turma.Turma;
import br.edu.ifsul.cstsi.tads_curso.api.turma.TurmaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/matriculas")
public class MatriculaController {

    @Autowired
    private MatriculaService service;
    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private TurmaRepository turmaRepository;

    @GetMapping
    public ResponseEntity<Page<MatriculaDTOResponse>> selectAll(@PageableDefault(size=50,sort = "data") Pageable paginacao) {
        return ResponseEntity.ok(service.getMatriculas(paginacao).map(MatriculaDTOResponse::new));
    }

    @GetMapping("{id}")
    public ResponseEntity<MatriculaDTOResponse> selectById(@PathVariable("id") Long id){
        var m = service.getMatriculaById(id);
        if(m.isPresent()){
            return ResponseEntity.ok(new MatriculaDTOResponse(m.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<URI> insert(@RequestBody MatriculaDTOPost matriculaDTOPost, UriComponentsBuilder uriBuilder){

        if(matriculaDTOPost.alunoId() == null) {
            throw new IllegalArgumentException("O ID do aluno não pode ser nulo");
        }
        Aluno aluno = alunoRepository.findById(matriculaDTOPost.alunoId())
                .orElseThrow(() -> new ResourceNotFoundException("Aluno não encontrado"));

        if(matriculaDTOPost.turmaId() == null) {
            throw new IllegalArgumentException("O ID da turma não pode ser nulo");
        }
        Turma turma = turmaRepository.findById(matriculaDTOPost.turmaId())
                .orElseThrow(() -> new ResourceNotFoundException("Turma não encontrada"));

        var m = service.insert(new Matricula(
                null,
                matriculaDTOPost.data(),
                matriculaDTOPost.forma_pagto(),
                matriculaDTOPost.num_mat(),
                aluno,
                turma
        ));
        var location = uriBuilder.path("api/v1/matriculas/{id}").buildAndExpand(m.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<MatriculaDTOResponse> update(@PathVariable("id") Long id, @Valid @RequestBody MatriculaDTOPut matriculaDTOPut){

        if(matriculaDTOPut.alunoId() == null) {
            throw new IllegalArgumentException("O ID do aluno não pode ser nulo");
        }
        Aluno aluno = alunoRepository.findById(matriculaDTOPut.alunoId())
                .orElseThrow(() -> new ResourceNotFoundException("Aluno não encontrado"));

        if(matriculaDTOPut.turmaId() == null) {
            throw new IllegalArgumentException("O ID da turma não pode ser nulo");
        }
        Turma turma = turmaRepository.findById(matriculaDTOPut.turmaId())
                .orElseThrow(() -> new ResourceNotFoundException("Turma não encontrada"));

        var m = service.update(new Matricula(
                id,
                matriculaDTOPut.data(),
                matriculaDTOPut.forma_pagto(),
                matriculaDTOPut.num_mat(),
                aluno,
                turma
        ));
        return m != null ?
                ResponseEntity.ok(new MatriculaDTOResponse(m)):
                ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable("id") Long id){
        return service.delete(id) ?
                ResponseEntity.ok().build() :
                ResponseEntity.notFound().build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
