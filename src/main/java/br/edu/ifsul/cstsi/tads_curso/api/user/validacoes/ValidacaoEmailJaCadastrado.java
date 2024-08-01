package br.edu.ifsul.cstsi.tads_curso.api.user.validacoes;

import br.edu.ifsul.cstsi.tads_curso.api.infra.exception.ValidacaoEmailJaCadastradoException;
import br.edu.ifsul.cstsi.tads_curso.api.user.Usuario;
import br.edu.ifsul.cstsi.tads_curso.api.user.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoEmailJaCadastrado implements ValidacaoCadastroDeUsuario{
    @Autowired //indica ao Spring Boot que ele deve injetar essa dependência para a classe funcionar
    private UsuarioRepository rep;

    @Override
    public void validar(Usuario usuario) {
        if (rep.existsByEmail(usuario.getEmail())) {
            throw new ValidacaoEmailJaCadastradoException("Erro: Email já está cadastrado.");
        }
    }
}
