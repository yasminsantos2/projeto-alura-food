package br.com.alurafood.pagamentos.controller;


import br.com.alurafood.pagamentos.dto.PagamentoDto;
import br.com.alurafood.pagamentos.repository.PagamentoRepository;
import br.com.alurafood.pagamentos.service.PagamentoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping ("/pagamentos")
@Tag(name = "Pagamentos", description = "Endpoints para gerenciamento de pagamentos")
public class PagamentoController {


    @Autowired
    private PagamentoService service;


    @GetMapping
    @Operation(summary = "Listar todos os pagamentos", description = "Retorna uma página com os pagamentos cadastrados")
    public Page<PagamentoDto> listar(@PageableDefault(size = 10) Pageable paginacao) {
        return service.obterTodos(paginacao);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pagamento por ID", description = "Retorna os detalhes de um pagamento específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pagamento encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pagamento não encontrado")
    })
    public ResponseEntity<PagamentoDto> detalhar(@PathVariable @NotNull Long id) {
        PagamentoDto dto = service.obterPorId(id);
        return ResponseEntity.ok(dto);
    }


    @PostMapping
    @Operation(summary = "Criar um novo pagamento", description = "Cadastra um novo pagamento no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pagamento criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<PagamentoDto> cadastrar(@RequestBody @Valid PagamentoDto dto, UriComponentsBuilder uriBuilder) {
        PagamentoDto pagamento = service.criarPagamento(dto);
        URI endereco = uriBuilder.path("/pagamentos/{id}").buildAndExpand(pagamento.getId()).toUri();

        return ResponseEntity.created(endereco).body(pagamento);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um pagamento", description = "Altera os dados de um pagamento existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pagamento atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pagamento não encontrado")
    })
    public ResponseEntity<PagamentoDto> atualizar(@PathVariable @NotNull Long id, @RequestBody @Valid PagamentoDto dto) {
        PagamentoDto atualizado = service.atualizarPagamento(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover um pagamento", description = "Exclui um pagamento do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pagamento removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pagamento não encontrado")
    })
    public ResponseEntity<PagamentoDto> remover(@PathVariable @NotNull Long id) {
        service.excluirPagamento(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/confirmar")
    @Operation(summary = "Confirmar pagamento", description = "Marca o pagamento como confirmado")
    public void confirmarPagamento(@PathVariable @NotNull Long id){
        service.confirmarPagamento(id);
    }

    @GetMapping("/porta")
    @Operation(summary = "Obter porta da instância", description = "Retorna a porta em que a instância do microserviço está rodando")
    public String retornarPorta(@Value("${local.server.port}") String porta) {
        return String.format("Requisição respondida pela instância executando na porta %s", porta);
    }
}
