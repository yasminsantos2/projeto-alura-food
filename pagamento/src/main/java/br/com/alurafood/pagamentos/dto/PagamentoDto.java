package br.com.alurafood.pagamentos.dto;

import br.com.alurafood.pagamentos.model.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PagamentoDto {
    @Schema(example = "1")
    private Long id;

    @NotNull
    @Positive
    @Schema(example = "100.00")
    private BigDecimal valor;

    @NotBlank
    @Size(max=100)
    @Schema(example = "Yasmin Santos")
    private String nome;

    @NotBlank
    @Size(max=19)
    @Schema(example = "1234567890123456")
    private String numero;

    @NotBlank
    @Size(max=7)
    @Schema(example = "12/26")
    private String expiracao;

    @NotBlank
    @Size(min=3, max=3)
    @Schema(example = "123")
    private String codigo;

    @Schema(example = "CRIADO")
    private Status status;

    @NotNull
    @Schema(example = "1")
    private Long formaDePagamentoId;

    @NotNull
    @Schema(example = "1")
    private Long pedidoId;
}
