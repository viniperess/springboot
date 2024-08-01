package br.edu.ifsul.cstsi.tads_curso.api.infra.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
public class TratadorDeErros extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity trataErro400(ConstraintViolationException ex){
        var erros = ex.getConstraintViolations();
        return ResponseEntity.badRequest().body(erros.stream().map(ErroValidation::new).toList());
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity trataErro404() { //404 - Not Found
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity trataErro400() { //400 - Bad Request
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(ValidacaoEmailJaCadastradoException.class)
    public ResponseEntity trataErro400(ValidacaoEmailJaCadastradoException ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    private record ErroValidation(
        String campo,
        String mensagem) {
        public ErroValidation(ConstraintViolation<?> erro){
            this(erro.getPropertyPath().toString(), erro.getMessage());
        }
    }
}