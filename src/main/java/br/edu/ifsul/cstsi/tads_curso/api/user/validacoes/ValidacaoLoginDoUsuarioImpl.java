package br.edu.ifsul.cstsi.tads_curso.api.user.validacoes;

import br.edu.ifsul.cstsi.tads_curso.api.auth.UsuarioDTO;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoLoginDoUsuarioImpl implements ValidacaoLoginDoUsuario {

    @Override
    public void validar(UsuarioDTO usuarioDTO) {
        if (usuarioDTO.email() == null || !usuarioDTO.email().contains("@")) {
            throw new IllegalArgumentException("E-mail inválido");
        }

        if (usuarioDTO.senha() == null || usuarioDTO.senha().length() < 8) {
            throw new IllegalArgumentException("Senha muito curta");
        }
    }
}
