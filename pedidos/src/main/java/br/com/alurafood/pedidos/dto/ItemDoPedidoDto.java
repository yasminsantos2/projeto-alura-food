package br.com.alurafood.pedidos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemDoPedidoDto {

    @Schema(example = "1")
    private Long id;

    @NotNull
    @Positive
    @Schema(example = "2")
    private Integer quantidade;

    @NotBlank
    @Schema(example = "Hambúrguer de Siri")
    private String descricao;
}
