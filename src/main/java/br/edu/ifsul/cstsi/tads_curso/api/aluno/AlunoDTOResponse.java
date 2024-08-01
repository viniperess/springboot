package br.edu.ifsul.cstsi.tads_curso.api.aluno;

public record AlunoDTOResponse(
        Long id,
        String nome,
        String cpf,
        String rg,
        String endereco,
        String fone
) {
    public AlunoDTOResponse(Aluno aluno){
        this(aluno.getId(), aluno.getNome(), aluno.getCpf(), aluno.getRg(), aluno.getEndereco(), aluno.getFone());
    }
}
