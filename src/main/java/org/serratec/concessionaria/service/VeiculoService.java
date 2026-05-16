package org.serratec.concessionaria.service;

import org.serratec.concessionaria.entity.Veiculo;
import org.serratec.concessionaria.exception.RegraNegocioException;
import org.serratec.concessionaria.exception.ResourceNotFoundException;
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

    public Veiculo buscarPorId(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado: Veículo com o ID fornecido não existe."));
    }

    public Veiculo salvar(Veiculo veiculo) {
        // antes de salvar tem que ver se ta dentro das regras de negócio
        validarRegrasDeVenda(veiculo);

        // vê se tão burlando as regrinhas do DETRAN clonando placa
        if (repository.findByPlaca(veiculo.getPlaca()).isPresent()) {
            throw new RegraNegocioException("Dados inválidos: A placa informada já está cadastrada no sistema.");
        }

        return repository.save(veiculo);
    }
    public Veiculo atualizar(UUID id, Veiculo veiculoAtualizado) {
        Veiculo veiculoExistente = buscarPorId(id);

        if (!veiculoExistente.getPlaca().equals(veiculoAtualizado.getPlaca())) {
            if (repository.findByPlaca(veiculoAtualizado.getPlaca()).isPresent()) {
                throw new RegraNegocioException("Dados inválidos: A nova placa informada já pertence a outro veículo.");
            }
        }
        validarRegrasDeVenda(veiculoAtualizado);
        veiculoExistente.setMarca(veiculoAtualizado.getMarca());
        veiculoExistente.setModelo(veiculoAtualizado.getModelo());
        veiculoExistente.setAno(veiculoAtualizado.getAno());
        veiculoExistente.setValor(veiculoAtualizado.getValor());
        veiculoExistente.setPlaca(veiculoAtualizado.getPlaca());
        veiculoExistente.setMaximoDesconto(veiculoAtualizado.getMaximoDesconto());
        veiculoExistente.setVendido(veiculoAtualizado.getVendido());
        veiculoExistente.setValorVenda(veiculoAtualizado.getValorVenda());
        veiculoExistente.setCliente(veiculoAtualizado.getCliente());

        return repository.save(veiculoExistente);
    }
    // aqui é a validação, as regras são:
    // não da pra vender o que ja foi vendido
    // o valor não pode exceder o minimo e o calculo é depois de considerar o desconto (vendedor não pode fazer uma caridade excessiva, o sistema é mau)
    // tem que tratar os vendedores com educação (essa Validation é feita na loja fisicamente)
    private void validarRegrasDeVenda(Veiculo v) {
        // seguindo a regra do Swagger: "Obrigatório se vendido for True"
        if (v.getVendido()) {
            if (v.getValorVenda() == null) {
                throw new RegraNegocioException("Dados inválidos: O valor de venda é obrigatório quando o veículo está marcado como vendido.");
            }

            // regrinha do desconto
            double valorMinimoPermitido = v.getValor() - v.getMaximoDesconto();

            if (v.getValorVenda() < valorMinimoPermitido) {
                throw new RegraNegocioException("Dados inválidos: O valor de venda está abaixo do limite mínimo permitido com base no desconto máximo.");
            }
        }
    }

    // aqui apaga o veiculo pelo id, simples
    public void deletar(UUID id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado: Não é possível deletar um veículo inexistente.");
        }
        repository.deleteById(id);
    }
}