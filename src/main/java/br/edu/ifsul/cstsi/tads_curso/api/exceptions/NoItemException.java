package br.edu.ifsul.cstsi.tads_curso.api.exceptions;

public class NoItemException extends RuntimeException {
    public NoItemException(String message) {
        super(message);
    }
}