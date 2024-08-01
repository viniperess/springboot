package br.edu.ifsul.cstsi.tads_curso.api.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Boolean existsByEmail(String email);

    Usuario findByEmail(String email);
}
