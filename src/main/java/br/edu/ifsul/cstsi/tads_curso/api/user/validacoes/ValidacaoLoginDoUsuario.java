package br.edu.ifsul.cstsi.tads_curso.api.user.validacoes;


import br.edu.ifsul.cstsi.tads_curso.api.auth.UsuarioDTO;

public interface ValidacaoLoginDoUsuario {
    void validar(UsuarioDTO usuarioDTO);
}
