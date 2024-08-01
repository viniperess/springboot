package br.edu.ifsul.cstsi.tads_curso.api.curso;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/cursos")
public class CursoController {

    @Autowired
    private CursoService service;

    @GetMapping
    public ResponseEntity<Page<CursoDTOResponse>> selectAll(@PageableDefault(size = 50, sort = "nome") Pageable paginacao) {
        return ResponseEntity.ok(service.getCursos(paginacao).map(CursoDTOResponse::new));
    }

    @GetMapping("{id}")
    public ResponseEntity<CursoDTOResponse> selectById(@PathVariable("id") Long id) {
        var c = service.getCursoById(id);
        if(c.isPresent()){
            return ResponseEntity.ok(new CursoDTOResponse(c.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<CursoDTOResponse>> selectByNome(@PathVariable("nome") String nome) {
        var cursos = service.getCursosByNome(nome);
        return cursos.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(cursos.stream().map(CursoDTOResponse::new).collect(Collectors.toList()));
    }
    @PostMapping
    public ResponseEntity<URI> insert(@RequestBody CursoDTOPost cursoDTOPost, UriComponentsBuilder uriBuilder){
        var c = service.insert(new Curso(
                null,
                cursoDTOPost.nome(),
                cursoDTOPost.pre_requisito(),
                cursoDTOPost.carga_hora(),
                cursoDTOPost.valor(),
                null
        ));
        var location = uriBuilder.path("api/v1/cursos/{id}").buildAndExpand(c.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<CursoDTOResponse> update(@PathVariable("id") Long id, @Valid @RequestBody CursoDTOPut cursoDTOPut){
        var c = service.update(new Curso(
                id,
                cursoDTOPut.nome(),
                cursoDTOPut.pre_requisito(),
                cursoDTOPut.carga_hora(),
                cursoDTOPut.valor(),
                null
        ));
        return c != null ?
                ResponseEntity.ok(new CursoDTOResponse(c)):
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
