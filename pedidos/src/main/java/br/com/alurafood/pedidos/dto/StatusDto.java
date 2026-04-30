package br.com.alurafood.pedidos.dto;

import br.com.alurafood.pedidos.model.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatusDto {
    @Schema(example = "CONFIRMADO")
    private Status status;
}
