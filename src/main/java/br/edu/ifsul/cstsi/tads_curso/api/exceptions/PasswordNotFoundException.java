package br.edu.ifsul.cstsi.tads_curso.api.exceptions;

public class PasswordNotFoundException extends RuntimeException{
    public PasswordNotFoundException(String message){
        super(message);
    }
}
