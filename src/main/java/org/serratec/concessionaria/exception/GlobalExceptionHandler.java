package org.serratec.concessionaria.exception;

// imports necessários do Spring para fazer a interceptação e as respostas HTTP
// é tipo um jogo de vôlei onde a bola é cortada e muda de direção, o service levanta,
// o exception faz um passe e o Spring aqui no Handler marca o ponto
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


 // a anotação @RestControllerAdvice avisa ao Spring Boot que esta classe é o
 // controlador global de erros da API, ficando de olho em todos os Controllers
 // se qualquer método disparar uma Exception, essa classe entra em ação antes de o erro ir pra tela, interceptando

@RestControllerAdvice
public class GlobalExceptionHandler {

     // interceptador exclusivo para quando o sistema disparar a nossa 'ResourceNotFoundException' (Erro 404)

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErroResposta> tratarResourceNotFound(ResourceNotFoundException ex) {

        // criamos o nosso record ErroResposta passando o status do erro, o nome amigável e a mensagem do Service
        ErroResposta erro = new ErroResposta(
                HttpStatus.NOT_FOUND.value(),       // código 404
                "Not Found",                        // nome técnico do status HTTP (lembrando dos gatinhos)
                ex.getMessage()                     // captura a mensagem enviada pelo super() na exception
        );

        // devolve o JSON moldado pro Swagger/Postman com o status HTTP 404 apropriado
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }


     // interceptador para falhas de regras de negócio, CPFs/placas duplicadas ou descontos abusivos (Erro 400)

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<ErroResposta> tratarRegraNegocio(RegraNegocioException ex) {

        ErroResposta erro = new ErroResposta(
                HttpStatus.BAD_REQUEST.value(),     // código 400
                "Bad Request",                      // nome técnico do status HTTP
                ex.getMessage()                     // captura o texto do erro vindo direto das validações
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }


     // aqui é a segurança. se acontecer qualquer erro bizarro e inesperado no servidor
     // (erro de digitação no banco, estouro de memória, nulo onde não devia que nao deve rolar o NullPointerException felizmente... eu acho... assim eu sou muito ruim em Java entao pode rolar), cai aqui para o sistema não mostrar código cru e feio na tela do usuário (Erro 500)

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResposta> tratarErroGeral(Exception ex) {

        ErroResposta erro = new ErroResposta(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), // código 500
                "Internal Server Error",
                "Ocorreu um erro interno inesperado no sistema. Por favor, tente novamente mais tarde."
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }
}