package org.serratec.concessionaria.service;

import org.serratec.concessionaria.entity.Veiculo;
import org.serratec.concessionaria.repository.VeiculoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VeiculoService {

    // mantendo aquele padrãozinho Jaé
    private final VeiculoRepository repository;

    public VeiculoService(VeiculoRepository repository) {
        this.repository = repository;
    }

    // listando de acordo com os filtros que foi requisitado no Swagger
    public List<Veiculo> listar(String placa, String marca, String modelo) {
        if (placa != null) {
            return repository.findByPlaca(placa).stream().toList();
        }
        if (marca != null) {
            return repository.findByMarcaContainingIgnoreCase(marca);
        }
        if (modelo != null) {
            return repository.findByModeloContainingIgnoreCase(modelo);
        }
        return repository.findAll();
    }

    // aqui pegando no nosso Optionalzinho
    public Optional<Veiculo> buscarPorId(UUID id) {
        return repository.findById(id);
    }

    public Veiculo salvar(Veiculo veiculo) {
        // antes de salvar tem que ver se ta dentro das regras
        // "que regras?" verás...
        validarRegrasDeVenda(veiculo);

        // vê se tão burlando as regrinhas do DETRAN clonando placa
        if (repository.findByPlaca(veiculo.getPlaca()).isPresent()) {
            throw new RuntimeException("Placa duplicada detectada, enviando Autobots, Transformers do DETRAN, pra sua casa...");
        }

        // salvando o minino respeitoso que ta dentro das regras
        return repository.save(veiculo);
    }

    // aqui é a validação, as regras são:
    // não da pra vender o que ja foi vendido
    // o valor não pode exceder o minimo e o calculo é depois de considerar o desconto (vendedor não pode fazer uma caridade excessiva, o sistema é mau)
    // tem que tratar os vendedores com educação (essa Validation é feita na loja fisicamente)
    private void validarRegrasDeVenda(Veiculo v) {
        // seguindo a regra do Swagger: "Obrigatório se vendido for True"
        if (v.getVendido()) {
            if (v.getValorVenda() == null) {
                throw new RuntimeException("calmaí, vendemos um carro e o vendedor não marcou o valor? chama o gerente");
            }

            // regrinha do desconto, o valor da venda não pode ser menor que o valor menos o desconto
            // ex.: vale 50k e tem até 5k de desconto, o mínimo é 45k. se vender menos que isso
            // ta tentando fazer alguma ajudinha extra, as vezes é da família ou amigo, mas não pode
            double valorMinimoPermitido = v.getValor() - v.getMaximoDesconto();

            if (v.getValorVenda() < valorMinimoPermitido) {
                throw new RuntimeException("valor de venda menor que o mínimo, já avisei que vai dar problema ficar barateando pros primo");
            }
        }
    }

    // aqui apaga o veiculo pelo id, simples
    public void deletar(UUID id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new RuntimeException("onde está o veiculo... nao acho em lugar nenhum...");
        }
    }
}


// claro, essas mensagens são temporarias teacher cadu, quando eu fizer os exception vou colocar mensagens sérias, prometo