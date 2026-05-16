// esse é o primeiro que vou fazer e subir, talvez eu mexa mais no futuro
// mas como é o primeiro, vou avisar aqui que vou encher o projeto de comentários
// pra explicar cada parte, demonstrar que sei o que to fazendo,
// já que não vai ter apresentação (ainda bem, tenho vergonha)
package org.serratec.concessionaria.entity; // package que é o caminho, igual no projeto de java/poo

import jakarta.persistence.*; // carinha do JPA e Hibernate, o que joga pro bd
import jakarta.validation.constraints.*; // o carinha que verifica as parada, validação
import lombok.Data; // o carinha que facilita nossa vida gerando getter e setter na execução
import lombok.AllArgsConstructor; // ele tb cria os construtores, com e sem args
import lombok.NoArgsConstructor;
import java.util.UUID; // o carinha que vai ajudar com os UUID automaticos
import java.util.List;

@Entity // avisa pro spring criar a tabelinha no bd
@Data // o carinha do lombok que faz getter setter e tals
@AllArgsConstructor // e os do lombok pros constructors
@NoArgsConstructor
public class Cliente {

    @Id // paradinha que faz com que o GenerationType gere o UUID automaticamente e faz dele o ID
    @GeneratedValue(strategy = GenerationType.AUTO)
    // existe também, conforme o Leilton mostrou, o GenerationType.UUID
    // mas como esse é mais moderno e pra versões mais recentes do Hibernate e Spring
    // to usando o GenerationType.AUTO. e também pra ficar diferente do dele, vai que me acusam
    private UUID id;

    // O resto é padrão né, não vou ocupar muitas linhas com comentários
    @NotBlank
    @Column(nullable=false)
    private String nome;

    @NotBlank
    @Column(nullable=false)
    private String telefone;

    @NotBlank
    @Column(unique = true, nullable=false)
    private String cpf;

    @NotBlank
    @Email
    @Column(nullable=false)
    private String email;

    // um cliente pode ter vários veículos etc
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Veiculo> veiculos;
}
