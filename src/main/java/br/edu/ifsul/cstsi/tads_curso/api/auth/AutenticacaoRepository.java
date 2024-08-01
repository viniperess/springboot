package br.edu.ifsul.cstsi.tads_curso.api.auth;

import br.edu.ifsul.cstsi.tads_curso.api.user.Usuario;
import org.springframework.data.repository.Repository;
public interface AutenticacaoRepository extends Repository<Usuario,Long> {
    Usuario findByEmail(String email);
}
