package br.com.alurafood.pedidos.controller;

import br.com.alurafood.pedidos.dto.PedidoDto;
import br.com.alurafood.pedidos.dto.StatusDto;
import br.com.alurafood.pedidos.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
@Tag(name = "Pedidos", description = "Endpoints para gerenciamento de pedidos")
public class PedidoController {

        @Autowired
        private PedidoService service;

        @GetMapping()
        @Operation(summary = "Listar todos os pedidos", description = "Retorna uma lista com todos os pedidos cadastrados")
        public List<PedidoDto> listarTodos() {
            return service.obterTodos();
        }

        @GetMapping("/{id}")
        @Operation(summary = "Buscar pedido por ID", description = "Retorna os detalhes de um pedido específico")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Pedido encontrado com sucesso"),
                @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
        })
        public ResponseEntity<PedidoDto> listarPorId(@PathVariable @NotNull Long id) {
            PedidoDto dto = service.obterPorId(id);

            return  ResponseEntity.ok(dto);
        }

        @PostMapping()
        @Operation(summary = "Realizar um novo pedido", description = "Cria um novo pedido no sistema")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso"),
                @ApiResponse(responseCode = "400", description = "Dados inválidos")
        })
        public ResponseEntity<PedidoDto> realizaPedido(
                @io.swagger.v3.oas.annotations.parameters.RequestBody(
                        content = @io.swagger.v3.oas.annotations.media.Content(
                                mediaType = "application/json",
                                examples = {
                                        @io.swagger.v3.oas.annotations.media.ExampleObject(
                                                name = "Pedido Simples",
                                                value = "{\"itens\": [{\"quantidade\": 1, \"descricao\": \"Hambúrguer de Siri\"}]}"
                                        ),
                                        @io.swagger.v3.oas.annotations.media.ExampleObject(
                                                name = "Combo Duplo",
                                                value = "{\"itens\": [{\"quantidade\": 2, \"descricao\": \"Hambúrguer de Siri\"}, {\"quantidade\": 1, \"descricao\": \"Batata Frita\"}]}"
                                        ),
                                        @io.swagger.v3.oas.annotations.media.ExampleObject(
                                                name = "Pedido Família",
                                                value = "{\"itens\": [{\"quantidade\": 4, \"descricao\": \"Pizza G\"}, {\"quantidade\": 2, \"descricao\": \"Coca-Cola 2L\"}]}"
                                        )
                                }
                        )
                )
                @RequestBody @Valid PedidoDto dto, UriComponentsBuilder uriBuilder) {
            PedidoDto pedidoRealizado = service.criarPedido(dto);

            URI endereco = uriBuilder.path("/pedidos/{id}").buildAndExpand(pedidoRealizado.getId()).toUri();

            return ResponseEntity.created(endereco).body(pedidoRealizado);

        }

        @PutMapping("/{id}/status")
        @Operation(summary = "Atualizar status do pedido", description = "Altera o status de um pedido existente")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso"),
                @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
        })
        public ResponseEntity<PedidoDto> atualizaStatus(@PathVariable Long id, @RequestBody StatusDto status){
           PedidoDto dto = service.atualizaStatus(id, status);

            return ResponseEntity.ok(dto);
        }


        @PutMapping("/{id}/pago")
        @Operation(summary = "Aprovar pagamento do pedido", description = "Marca o pedido como pago")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Pagamento aprovado com sucesso"),
                @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
        })
        public ResponseEntity<Void> aprovaPagamento(@PathVariable @NotNull Long id) {
            service.aprovaPagamentoPedido(id);

            return ResponseEntity.ok().build();
        }

        @GetMapping("/porta")
        @Operation(summary = "Obter porta da instância", description = "Retorna a porta em que a instância do microserviço está rodando")
        public String retornarPorta(@Value("${local.server.port}") String porta) {
            return String.format("Requisição respondida pela instância executando na porta %s", porta);
        }
}
