package org.serratec.concessionaria.repository;

import org.serratec.concessionaria.entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface VeiculoRepository extends JpaRepository<Veiculo, UUID> {

    // mesmo esquema do anterior pra seguir o Swagger

    // como a placa é única, aqui fica simples (nada de duplicar placa, alô DETRAN)
    // o uso do Optional aqui é pra evitar o NullPointerException, que pode rolar
    // se ele não achar nenhum veiculo com essa placa, por ser único.
    // pra evitar retorno de null

    // basicamente, "me diz se tem tal coisa e se não tiver nem me responde"
    Optional<Veiculo> findByPlaca(String placa);

    // marca e modelo tem de monte, ainda mais os populares.
    // comentários v2 pq acho que não expliquei bem da ultima vez:
    // pra explicar bem e não deixar isso igual minha cara,
    // o List basicamente faz uma busca completa e retorna em um conjunto de dados
    // que é o List, aqueles []. faz uma limpa, sabe?
    // o Containing é o que age como um LIKE do SQL, ele busca o que "contém" aquele input
    // e o IgnoreCase é autoexplicativo, ele ignora diferentes casings
    // como cAdU ou Cadu ou CADU ou caDU etc.
    List<Veiculo> findByMarcaContainingIgnoreCase(String marca);
    List<Veiculo> findByModeloContainingIgnoreCase(String modelo);
}