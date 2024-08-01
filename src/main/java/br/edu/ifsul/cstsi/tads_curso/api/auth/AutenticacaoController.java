package br.edu.ifsul.cstsi.tads_curso.api.auth;

import br.edu.ifsul.cstsi.tads_curso.api.infra.security.TokenJwtDTO;
import br.edu.ifsul.cstsi.tads_curso.api.infra.security.TokenService;
import br.edu.ifsul.cstsi.tads_curso.api.user.Usuario;
import br.edu.ifsul.cstsi.tads_curso.api.user.validacoes.ValidacaoLoginDoUsuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private List<ValidacaoLoginDoUsuario> validacoes;

    @PostMapping
    public ResponseEntity<TokenJwtDTO> efetuaLogin(@Valid @RequestBody UsuarioDTO data){
        var authenticationDTO = new UsernamePasswordAuthenticationToken(data.email(), data.senha());

        validacoes.forEach(v -> v.validar(data));

        var authentication = manager.authenticate(authenticationDTO);
        var tokenJWT = tokenService.geraToken((Usuario) authentication.getPrincipal());
        return ResponseEntity.ok(new TokenJwtDTO(tokenJWT));
    }
}
