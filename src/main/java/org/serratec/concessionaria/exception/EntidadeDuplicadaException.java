package org.serratec.concessionaria.exception;
// pra lidar com cpf e placas que sao iguais em certas requisiçoes
public class EntidadeDuplicadaException extends RuntimeException {
    public EntidadeDuplicadaException(String message) {
        super(message);
    }
}