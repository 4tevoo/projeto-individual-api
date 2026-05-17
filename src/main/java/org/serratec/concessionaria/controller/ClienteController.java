package org.serratec.concessionaria.controller;

import jakarta.validation.Valid;
import org.serratec.concessionaria.entity.Cliente;
import org.serratec.concessionaria.model.ClienteInput;
import org.serratec.concessionaria.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cliente") // urlzinha base
public class ClienteController {

    private final ClienteService service;

    // fazendo por constructor seguindo a recomendação do prof
    // posso ta lembrando errado (é altamente provavel inlcusive, pessima memoria), mas vc disse que é melhor que @Autowired né

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    // GET /cliente?nome=...&cpf=...
    @GetMapping
    public ResponseEntity<List<Cliente>> listar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String cpf) {

        List<Cliente> clientes = service.listar(nome, cpf);
        return ResponseEntity.ok(clientes); // Status 200 OK
    }

    // GET /cliente/{id} (caso queira buscar um específico por id)
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable UUID id) {
        Cliente cliente = service.buscarPorId(id);
        return ResponseEntity.ok(cliente); // Status 200 OK
    }

    // POST /cliente
    @PostMapping
    public ResponseEntity<Cliente> cadastrar(@Valid @RequestBody ClienteInput dto) {
        // o @Valid garante que os @NotBlank e @Email do ClienteInput sejam validados aqui na entrada
        Cliente clienteSalvo = service.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo); // Status 201 Created
    }

    // DELETE /cliente/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build(); // Status 204 No Content
    }
}