package org.serratec.concessionaria.entity;

// Imports necessários
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Entity // esse faz o banco saber que é uma tabela
@Data // pro lombok trabalhar pra nós pq programador quer evitar o máximo de esforço sempre
@AllArgsConstructor // construtor tudão
@NoArgsConstructor // construtor nadinha
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotBlank(message = "A marca é obrigatória")
    private String marca;

    @NotBlank(message = "O modelo é obrigatório")
    private String modelo;

    @NotNull
    @Min(1900) // não vamo ta pegando carro TÃO antigo assim né
    private Integer ano;

    @NotNull
    @DecimalMin("1.0") // vender carro a preço de banana tudo bem mas de graça não né
    private Double valor;

    @NotBlank
    @Column(unique = true) // nada de clonagem de placa aqui, se não DETRAN te pega
    private String placa;

    @NotNull
    @DecimalMin("0.0") // desconto de no mínimo 0%, ou seja, sem desconto, seguindo o Swagger
    private Double maximoDesconto;

    @NotNull
    private Boolean vendido;

    // seguindo as regrinhas do Swagger do prof Cadu, trato depois no service
    private Double valorVenda;

    // muitos carros podem ter um dono muito rico, aquele cliente que a loja gosta
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
}