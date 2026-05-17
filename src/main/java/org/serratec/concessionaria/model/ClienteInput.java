package org.serratec.concessionaria.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


 // record que representa o 'ClienteInput' do Swagger
 // usado como DTO de entrada para receber e validar os dados no cadastro de clientes (POST)

public record ClienteInput(

        @NotBlank(message = "O nome do cliente é obrigatório.")
        @Size(max = 255, message = "O nome não pode exceder 255 caracteres.")
        String nome,

        @NotBlank(message = "O telefone de contato é obrigatório.")
        String telefone,

        @NotBlank(message = "O CPF é obrigatório.")
        @Size(min = 11, max = 14, message = "O CPF deve conter um formato válido.")
        String cpf,

        @NotBlank(message = "O e-mail é obrigatório.")
        @Email(message = "O formato do e-mail informado é inválido.")
        String email
) {}// parte vazia pq o record cria sozinho