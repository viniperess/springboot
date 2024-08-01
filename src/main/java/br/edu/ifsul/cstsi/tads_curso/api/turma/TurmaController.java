package br.edu.ifsul.cstsi.tads_curso.api.turma;


import br.edu.ifsul.cstsi.tads_curso.api.curso.Curso;
import br.edu.ifsul.cstsi.tads_curso.api.curso.CursoRepository;
import br.edu.ifsul.cstsi.tads_curso.api.exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.Map;
import java.net.URI;

@RestController
@RequestMapping("api/v1/turmas")
public class TurmaController {

    @Autowired
    private TurmaService service;
    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping
    public ResponseEntity<Page<TurmaDTOResponse>> selectAll(@PageableDefault(size = 50, sort = "turno")Pageable paginacao){
        return ResponseEntity.ok(service.getTurmas(paginacao).map(TurmaDTOResponse::new));
    }

    @GetMapping("{id}")
    public ResponseEntity<TurmaDTOResponse> selectById(@PathVariable("id") Long id){
        var t = service.getTurmaById(id);
        if(t.isPresent()){
            return ResponseEntity.ok(new TurmaDTOResponse(t.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<URI> insert(@RequestBody TurmaDTOPost turmaDTOPost, UriComponentsBuilder uriBuilder){

        if (turmaDTOPost.cursoId() == null) {
            throw new IllegalArgumentException("O ID do curso não pode ser nulo");
        }

        Curso curso = cursoRepository.findById(turmaDTOPost.cursoId())
                .orElseThrow(() -> new ResourceNotFoundException("Curso não encontrado"));

        var t = service.insert(new Turma(
                null,
                turmaDTOPost.turno(),
                turmaDTOPost.dt_ini(),
                turmaDTOPost.dt_fim(),
                turmaDTOPost.hr_ini(),
                turmaDTOPost.hr_fim(),
                turmaDTOPost.qtd_vagas(),
                null,
                curso
        ));
        var location = uriBuilder.path("api/v1/turmas/{id}").buildAndExpand(t.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<TurmaDTOResponse> update(@PathVariable("id") Long id, @Valid @RequestBody TurmaDTOPut turmaDTOPut){
        if (turmaDTOPut.cursoId() == null) {
            throw new IllegalArgumentException("O ID do curso não pode ser nulo");
        }

        Curso curso = cursoRepository.findById(turmaDTOPut.cursoId())
                .orElseThrow(() -> new ResourceNotFoundException("Curso não encontrado"));


        var t = service.update(new Turma(
                id,
                turmaDTOPut.turno(),
                turmaDTOPut.dt_ini(),
                turmaDTOPut.dt_fim(),
                turmaDTOPut.hr_ini(),
                turmaDTOPut.hr_fim(),
                turmaDTOPut.qtd_vagas(),
                null,
                curso
        ));
        return t != null ?
                ResponseEntity.ok(new TurmaDTOResponse(t)):
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
