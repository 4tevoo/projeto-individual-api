package org.serratec.concessionaria.service;

import org.serratec.concessionaria.entity.Cliente;
import org.serratec.concessionaria.entity.Veiculo;
import org.serratec.concessionaria.exception.EntidadeDuplicadaException;
import org.serratec.concessionaria.exception.RegraNegocioException;
import org.serratec.concessionaria.exception.ResourceNotFoundException;
import org.serratec.concessionaria.model.VeiculoInsert;
import org.serratec.concessionaria.model.VeiculoUpdateInput;
import org.serratec.concessionaria.repository.ClienteRepository;
import org.serratec.concessionaria.repository.VeiculoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VeiculoService {

    private final VeiculoRepository repository;
    private final ClienteRepository clienteRepository;

    public VeiculoService(VeiculoRepository repository, ClienteRepository clienteRepository) {
        this.repository = repository;
        this.clienteRepository = clienteRepository;
    }

    // listando de acordo com os filtros que foi requisitado no Swagger
    public List<Veiculo> listar(String placa, String marca, String modelo) {
        if (placa != null) {
            Veiculo veiculo = repository.findByPlaca(placa)
                    .orElseThrow(() -> new ResourceNotFoundException("Veículo com a placa " + placa + " não foi localizado no sistema."));
            return List.of(veiculo);
        }
        if (marca != null) {
            List<Veiculo> resultado = repository.findByMarcaContainingIgnoreCase(marca);
            if (resultado.isEmpty()) {
                throw new ResourceNotFoundException("Nenhum veículo encontrado para a marca: " + marca);
            }
            return resultado;
        }
        if (modelo != null) {
            List<Veiculo> resultado = repository.findByModeloContainingIgnoreCase(modelo);
            if (resultado.isEmpty()) {
                throw new ResourceNotFoundException("Nenhum veículo encontrado para o modelo: " + modelo);
            }
            return resultado;
        }
        return repository.findAll();
    }

    public Veiculo buscarPorId(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado: Veículo com o ID fornecido não existe."));
    }

    public Veiculo salvar(VeiculoInsert dto) {

        // primeiro vendo se o dono do carro existe
        Cliente clienteDono = clienteRepository.findById(dto.clienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Não foi possível cadastrar o veículo: O cliente com o ID informado não foi encontrado."));

        // transforma o dto em entity
        Veiculo veiculo = new Veiculo();
        veiculo.setMarca(dto.marca());
        veiculo.setModelo(dto.modelo());
        veiculo.setAno(dto.ano());
        veiculo.setValor(dto.valor());
        veiculo.setPlaca(dto.placa());
        veiculo.setMaximoDesconto(dto.maximoDesconto());

        // bota o cliente no carro... não literalmente
        veiculo.setCliente(clienteDono);

        // faz ele nascer disponivel no estoque caso nao seja por alguma razão, double check né
        veiculo.setVendido(false);
        veiculo.setValorVenda(null);

        // roda a validação
        validarRegrasDeVenda(veiculo);

        // vê se tão burlando as regrinhas do DETRAN clonando placa
        if (repository.findByPlaca(veiculo.getPlaca()).isPresent()) {
            throw new EntidadeDuplicadaException("Dados inválidos: A placa informada já está cadastrada no sistema.");
        }

        // salva no banco
        return repository.save(veiculo);
    }

    public Veiculo atualizar(UUID id, VeiculoUpdateInput dto) {

        // vê se ta no banco
        Veiculo veiculoExistente = buscarPorId(id);

        // atualiza os campos permitidos no dto
        veiculoExistente.setMarca(dto.marca());
        veiculoExistente.setModelo(dto.modelo());
        veiculoExistente.setAno(dto.ano());
        veiculoExistente.setValor(dto.valor());
        veiculoExistente.setMaximoDesconto(dto.maximoDesconto());
        veiculoExistente.setVendido(dto.vendido());
        veiculoExistente.setValorVenda(dto.valorVenda());

        // placa e cliente não muda pra seguir o que o swagger manda

        // roda a validação de desconto e preço
        validarRegrasDeVenda(veiculoExistente);

        // SALVE!
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