package org.serratec.concessionaria.exception;

import java.time.LocalDateTime;
// esse aqui não é um exception em si, mas um molde do JSON que o postman vai receber na tela
// usando o record que substitui o método tradicional compilando  automaticamente os campos
public record ErroResposta(
        LocalDateTime dataHora, // guarda o momento exato em que o erro aconteceu no servidor
        Integer status,         // guarda o código numérico HTTP (Ex: 400, 404, 409)

        String erro,            // nome técnico do status HTTP (Ex: "Bad Request", "Not Found")
        String mensagem         // o texto e explicando o que deu errado
) {
    public ErroResposta(Integer status, String erro, String mensagem) {
        // o "this(...)" invoca o construtor principal do Record lá de cima,
        // carimbando a data e hora do sistema automaticamente no milissegundo em que o erro ocorreu
        this(LocalDateTime.now(), status, erro, mensagem);
    }
}