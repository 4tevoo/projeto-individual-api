package org.serratec.concessionaria.model;

import jakarta.validation.constraints.*;
import java.util.UUID;


 // esse Record representa o DTO de entrada para o cadastro de veículos (o POST)
 // feito direitinho com base no Swagger
 // usamos Record por ser uma estrutura leve, imutável e ideal para transferir dados
 // ela gera uma classe final com atributos private final por baixo dos panos, então encaixa aqui também

public record VeiculoInsert(

        @NotNull(message = "O ID do cliente proprietário é obrigatório.")
        UUID clienteId,

        @NotBlank(message = "A marca do veículo é obrigatória.")
        String marca,

        @NotBlank(message = "O modelo do veículo é obrigatório.")
        String modelo,

        @NotNull(message = "O ano do veículo é obrigatório.")
        @Min(value = 1900, message = "O ano do veículo não pode ser inferior a 1900.")
        Integer ano,

        @NotNull(message = "O valor base do veículo é obrigatório.")
        @DecimalMin(value = "1.0", message = "O valor mínimo do veículo deve ser R$ 1.00.")
        Double valor,

        @NotBlank(message = "A placa do veículo é obrigatória.")
        @Size(min=7, max=7, message="A placa precisa possuir apenas 7 dígitos sem incluir o traço no caso de placas antigas.")
        String placa,

        @NotNull(message = "O desconto máximo permitido é obrigatório.")
        @DecimalMin(value = "0.0", message = "O desconto máximo não pode ser um valor negativo.")
        Double maximoDesconto
) {}// parte vazia pq o record cria sozinho