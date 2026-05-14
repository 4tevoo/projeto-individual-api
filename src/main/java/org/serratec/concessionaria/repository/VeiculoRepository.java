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
    Optional<Veiculo> findByPlaca(String placa);

    // marca e modelo tem de monte, ainda mais os populares. então, LIST se eu nao me engano
    List<Veiculo> findByMarcaContainingIgnoreCase(String marca);
    List<Veiculo> findByModeloContainingIgnoreCase(String modelo);
}