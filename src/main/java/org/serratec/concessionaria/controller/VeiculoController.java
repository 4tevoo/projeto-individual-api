package org.serratec.concessionaria.controller;

import jakarta.validation.Valid;
import org.serratec.concessionaria.entity.Veiculo;
import org.serratec.concessionaria.model.VeiculoInsert;
import org.serratec.concessionaria.model.VeiculoUpdateInput;
import org.serratec.concessionaria.service.VeiculoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/veiculo") // pegando urlzinha
public class VeiculoController {

    private final VeiculoService service;

    public VeiculoController(VeiculoService service) {
        this.service = service;
    }

    // GET /veiculo?placa=...&marca=...&modelo=...
    @GetMapping
    public ResponseEntity<List<Veiculo>> listar(
            @RequestParam(required = false) String placa,
            @RequestParam(required = false) String marca,
            @RequestParam(required = false) String modelo) {

        List<Veiculo> veiculos = service.listar(placa, marca, modelo);
        return ResponseEntity.ok(veiculos); // Status 200 OK
    }

    // GET /veiculo/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Veiculo> buscarPorId(@PathVariable UUID id) {
        Veiculo veiculo = service.buscarPorId(id);
        return ResponseEntity.ok(veiculo); // Status 200 OK
    }

    // POST /veiculo
    @PostMapping
    public ResponseEntity<Veiculo> cadastrar(@Valid @RequestBody VeiculoInsert dto) {
        Veiculo veiculoSalvo = service.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(veiculoSalvo); // Status 201 Created
    }

    // PUT /veiculo/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Veiculo> atualizar(
            @PathVariable UUID id,
            @Valid @RequestBody VeiculoUpdateInput dto) {

        Veiculo veiculoAtualizado = service.atualizar(id, dto);
        return ResponseEntity.ok(veiculoAtualizado); // Status 200 OK
    }

    // DELETE /veiculo/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build(); // Status 204 No Content
    }
}