package br.com.alurafood.pedidos.dto;

import br.com.alurafood.pedidos.model.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDto {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "2024-04-30T10:00:00")
    private LocalDateTime dataHora;

    @Schema(example = "REALIZADO")
    private Status status;

    private List<ItemDoPedidoDto> itens = new ArrayList<>();



}
