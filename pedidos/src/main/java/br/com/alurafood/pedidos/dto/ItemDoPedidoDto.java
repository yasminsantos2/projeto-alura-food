package br.com.alurafood.pedidos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemDoPedidoDto {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "2")
    private Integer quantidade;

    @Schema(example = "Hambúrguer de Siri")
    private String descricao;
}
