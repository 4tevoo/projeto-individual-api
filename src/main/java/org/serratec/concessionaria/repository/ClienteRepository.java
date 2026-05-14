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
    Optional<Cliente> findByCpf(String cpf);

    // busquinha por nome, usando LIKE se eu nao me engano
    List<Cliente> findByNomeContainingIgnoreCase(String nome);
}