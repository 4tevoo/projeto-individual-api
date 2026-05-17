package org.serratec.concessionaria.model;

import jakarta.validation.constraints.*;


 // record baseado o 'VeiculoUpdateInput' do Swagger
 // usado estritamente para capturar os dados que vêm no corpo do PUT (Atualização)
 // sem placa e sem clienteId aqui dentro, blindando contra alterações erradas né

public record VeiculoUpdateInput(

        @NotBlank(message = "A marca do veículo é obrigatória para atualização.")
        String marca,

        @NotBlank(message = "O modelo do veículo é obrigatório para atualização.")
        String modelo,

        @NotNull(message = "O ano do veículo é obrigatório para atualização.")
        @Min(value = 1900, message = "O ano não pode ser anterior a 1900.")
        Integer ano,

        @NotNull(message = "O valor do veículo é obrigatório para atualização.")
        @DecimalMin(value = "1.0", message = "O valor do veículo deve ser de no mínimo R$ 1.00.")
        Double valor,

        @NotNull(message = "O desconto máximo permitido é obrigatório para atualização.")
        @DecimalMin(value = "0.0", message = "O desconto máximo não pode ser um valor negativo.")
        Double maximoDesconto,

        @NotNull(message = "O status de venda (true/false) deve ser informado.")
        Boolean vendido,

        // não precisa de muita validação pq  a regrinha
        // ("obrigatório se vendido for true") já ta dentro do Service
        Double valorVenda
) {}// parte vazia pq o record cria sozinho