package br.edu.ifsul.cstsi.tads_curso.api.user;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UsuarioController {

    private final UsuarioService service;
    private final PerfilRepository perfilRepository;

    public UsuarioController(UsuarioService service, PerfilRepository perfilRepository){
        this.service = service;
        this.perfilRepository = perfilRepository;
    }
    @PostMapping(path = "/api/v1/usuarios/cadastrar")
    @Transactional
    public ResponseEntity<String> cadastrar(@Valid @RequestBody UsuarioDTO usuarioDTO, UriComponentsBuilder uriBuilder){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12); //leia o comentário acima da classe para entender mais
        var usuario = new Usuario();
        usuario.setNome(usuarioDTO.nome());
        usuario.setSobrenome(usuarioDTO.sobrenome());
        usuario.setEmail(usuarioDTO.email());
        usuario.setSenha(encoder.encode(usuarioDTO.senha()));
        usuario.setPerfis(Arrays.asList(perfilRepository.findByNome("ROLE_USER")));
        try{
            var u = service.insert(usuario);
            var location = uriBuilder.path("api/v1/usuarios/cadastrar/{id}").buildAndExpand(u.getId()).toUri();
            return ResponseEntity.created(location).build();
        }catch (ValidationException e){
            return ResponseEntity.badRequest().body("Email já cadastrado");
        }
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