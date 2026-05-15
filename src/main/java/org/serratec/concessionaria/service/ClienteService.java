package org.serratec.concessionaria.service;

import org.serratec.concessionaria.entity.Cliente;
import org.serratec.concessionaria.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
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
    public Optional<Cliente> buscarPorId(UUID id) {
        // serve para quando o Controller pedir os detalhes de UM cliente específico
        // vou fazer o Controller ainda, aguarde teacher
        return repository.findById(id);
    }

    // aqui é o save
    public Cliente salvar(Cliente cliente) {
        // antes de salvar, ele usa o Repository pra ver se aquele CPF já existe
        // usando o isPresent pra ver se já tem pelo retorno to findByCpf
        if (repository.findByCpf(cliente.getCpf()).isPresent()) {
            // se o CPF existe, ele trava tudo e joga o erro
            // esse erro vai ser substituído pelo o que tiver no exception, que ainda vou fazer
            // "work in process" como dizem
            throw new RuntimeException("OW OW OW PARA AÍ!!! tem... dois de você?!");
        }
        // se passou pelo if, o caminho tá livre pra salvar no banco
        return repository.save(cliente);
    }

    // aqui é o carrasco, ele vai deletar o cliente... tadinho
    public void deletar(UUID id) {
        // primeiro vê se existe antes de apagar
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            // se não existir... como apaga o inexistente? reflexões filosóficas na madrugada
            throw new RuntimeException("Scooby-Doo, cadê você, meu filho?");
        }
    }
}

// ps.: calma, não vão ser essas as frases de exception de verdade, WORK IN PROCESS!!
// vou trocar pros exceptions quando eu chegar lá, é que eu sou lento...