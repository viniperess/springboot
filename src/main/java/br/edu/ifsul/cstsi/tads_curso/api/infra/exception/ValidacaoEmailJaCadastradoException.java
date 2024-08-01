package br.edu.ifsul.cstsi.tads_curso.api.infra.exception;

public class ValidacaoEmailJaCadastradoException extends RuntimeException {
    public ValidacaoEmailJaCadastradoException(String message) {
        super(message);
    }
}
