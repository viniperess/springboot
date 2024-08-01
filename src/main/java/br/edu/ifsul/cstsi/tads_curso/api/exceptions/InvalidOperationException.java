package br.edu.ifsul.cstsi.tads_curso.api.exceptions;

public class InvalidOperationException extends RuntimeException {
    public InvalidOperationException(String message) {
        super(message);
    }
}