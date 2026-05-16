package org.serratec.concessionaria.repository;

import org.serratec.concessionaria.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {

    // o tal do JPA faz o CRUDzinho que precisamos aqui então menos esforço né
    // mas como o Swagger ta pedindo coisas mais específicas, to declarando aqui

    // busquinha por CPF
    // o uso do Optional aqui é pra evitar o NullPointerException, que pode rolar
    // se ele não achar ninguém com esse cpf, por ser único.
    // pra evitar retorno de null

    // (comment v2 pq errei no entendimento dessa parte)
    // basicamente, "me mostra o que tem nessa caixa, seja ela vazia ou cheia"
    Optional<Cliente> findByCpf(String cpf);

    // busquinha por nome.
    // comentários v2 pq acho que não expliquei bem da ultima vez:
    // pra explicar bem e não deixar isso igual minha cara,
    // o List basicamente faz uma busca completa e retorna em um conjunto de dados
    // que é o List, aqueles []. faz uma limpa, sabe?
    // o Containing é o que age como um LIKE do SQL, ele busca o que "contém" aquele input
    // e o IgnoreCase é autoexplicativo, ele ignora diferentes casings
    // como cAdU ou Cadu ou CADU ou caDU etc.
    List<Cliente> findByNomeContainingIgnoreCase(String nome);
}