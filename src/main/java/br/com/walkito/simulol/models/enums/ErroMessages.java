package br.com.walkito.simulol.models.enums;

public enum ErroMessages {
    INTERNAL_SERVER_ERROR("Erro interno do sistema. Por favor tente novamente mais tarde"),
    EMAIL_INVALID("O e-mail informado é inválido. Por favor, informe um endereço de e-mail válido e ativo"),
    SESSION_NOT_FOUND("A Sessão de Jogo não foi encontrada. Por favor, tente novamente mais tarde"),
    USER_NOT_FOUND("Usuário não encontrado.");

    private String mensagemErro;

    ErroMessages(String mensagem){
        this.mensagemErro = mensagem;
    }

    public String getMensagemErro() {
        return mensagemErro;
    }
}
