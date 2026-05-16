package org.serratec.concessionaria.exception;


 // exceção customizada para recursos não localizados no banco de dados (aquele 404)
 // ela é disparada quando o usuário tenta buscar, atualizar ou deletar um ID
 // de Cliente ou Veículo que simplesmente não existe no sistema

public class ResourceNotFoundException extends RuntimeException {

    // serialização de novo
    private static final long serialVersionUID = 1L;

     // construtor que captura a mensagem detalhada informando qual recurso sumiu
    public ResourceNotFoundException(String message) {
        // aí passa o texto para a classe mãe (RuntimeException) guardar na memória
        // o GlobalExceptionHandler vai ler essa mensagem e colocar no JSON
        super(message);
    }
}