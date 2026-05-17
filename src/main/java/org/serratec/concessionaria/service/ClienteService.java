package org.serratec.concessionaria.service;

import org.serratec.concessionaria.entity.Cliente;
import org.serratec.concessionaria.exception.RegraNegocioException;
import org.serratec.concessionaria.exception.ResourceNotFoundException;
import org.serratec.concessionaria.model.ClienteInput;
import org.serratec.concessionaria.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class ClienteService {

    // fazendo daquele jeitinho sem o @Autowired pq o prof mostrou que era melhor
    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    // aqui é o nosso filtro
    public List<Cliente> listar(String nome, String cpf) {
        if (cpf != null) {
            // o findByCpf retorna um CPF né
            // o .stream().toList() transforma esse retorno em uma lista
            // que aí mantem o padrão de retorno do método (que é uma List)
            return repository.findByCpf(cpf).stream().toList();
        }
        // se não tem CPF mas tem Nome, usa a busca LIKE (Containing)
        if (nome != null) {
            return repository.findByNomeContainingIgnoreCase(nome);
        }
        // se o cara só deu um GET sem filtro nenhum, aí manda a lista compelta
        return repository.findAll();
    }

    // aqui é o buscador único
    public Cliente buscarPorId(UUID id) {
        // serve para quando o Controller pedir os detalhes de UM cliente específico
        // vou fazer o Controller ainda, aguarde teacher
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente com o ID " + id + " não foi localizado no sistema."));
    }

    // aqui é o save
    public Cliente salvar(ClienteInput dto) {
        // antes de salvar, ele usa o Repository pra ver se aquele CPF já existe
        // usando o isPresent pra ver se já tem pelo retorno to findByCpf
        if (repository.findByCpf(dto.cpf()).isPresent()) {
            // se o CPF existe, ele trava tudo e joga o erro
            // esse erro vai ser substituído pelo o que tiver no exception, que ainda vou fazer
            // "work in process" como dizem
            throw new RegraNegocioException("Não é possível cadastrar: O CPF " + dto.cpf() + " já pertence a um cliente ativo.");
        }
        // cria o cliente, transformando dto em entity
        Cliente cliente = new Cliente();
        cliente.setNome(dto.nome());
        cliente.setTelefone(dto.telefone());
        cliente.setCpf(dto.cpf());
        cliente.setEmail(dto.email());

        // se passou pelo if, o caminho tá livre pra salvar no banco
        return repository.save(cliente);
    }

    // aqui é o carrasco, ele vai deletar o cliente... tadinho
    public void deletar(UUID id) {
        // primeiro vê se existe antes de apagar
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Falha ao deletar: O ID " + id + " de cliente não existe.");
        }
        repository.deleteById(id);
    }
}