package br.edu.ifsul.cstsi.tads_curso.api.user;

public record UserDTOResponse(
        Long id,
        String nome,
        String username
) {
    public UserDTOResponse(Usuario user){
        this(user.getId(), user.getNome(), user.getUsername());
    }
}
