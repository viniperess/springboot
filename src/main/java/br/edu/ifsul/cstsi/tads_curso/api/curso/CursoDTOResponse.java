package br.edu.ifsul.cstsi.tads_curso.api.curso;


import java.math.BigDecimal;

public record CursoDTOResponse(
        String nome,
        String pre_requisito,
        Integer carga_hora,
        BigDecimal valor
) {
    public CursoDTOResponse(Curso curso){
        this(curso.getNome(), curso.getPre_requisito(), curso.getCarga_hora(), curso.getValor());
    }
}
