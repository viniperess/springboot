package br.edu.ifsul.cstsi.tads_curso.api.user;

import br.edu.ifsul.cstsi.tads_curso.api.user.validacoes.ValidacaoCadastroDeUsuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository rep;

    @Autowired
    private List<ValidacaoCadastroDeUsuario> validacoes;

    public Usuario insert(Usuario usuario) {
        Assert.isNull(usuario.getId(),"Não foi possível inserir o registro");

        validacoes.forEach(v -> v.validar(usuario));

        var usuarioSalvo = rep.save(usuario);

        return usuarioSalvo;
    }}