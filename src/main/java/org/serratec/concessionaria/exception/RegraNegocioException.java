package org.serratec.concessionaria.exception;


 // exceção customizada para erros de Regra de Negócio (Erros do Tipo 400 ou 409).
 // ela herda de RuntimeException para que o Java saiba que se trata de um erro de sistema
 // que acontece em tempo de execução, tirando a necessidade de blocos try/catch no Service

public class RegraNegocioException extends RuntimeException {

    // o Java pede esse ID de serialização para controle de versão do objeto em memória
    // no projeto de poo usamos @SupressWarnings('serial') pra parar de ficar amarelinho
    // mas confesso não entender muito sobre isso de serialização no exception
    // só sei que tem algo haver com log e no eclipse (sim eu usei o eclipse no poo, que tristeza)
    // dava alerta
    private static final long serialVersionUID = 1L;


     // construtor que recebe a mensagem de erro vinda direto da validação do Service

    public RegraNegocioException(String message) {
        // o "super(message)" envia o texto para a classe mãe (RuntimeException)
        // esse texto fica guardado na memória dentro do objeto da exceção
        // o nosso GlobalExceptionHandler vai capturar esse objeto e ler esse texto com o .getMessage()
        super(message);
    }
}