package br.com.alurafood.pagamentos.dto;

import br.com.alurafood.pagamentos.model.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PagamentoDto {
    @Schema(example = "1")
    private Long id;

    @Schema(example = "100.00")
    private BigDecimal valor;

    @Schema(example = "Yasmin Santos")
    private String nome;

    @Schema(example = "1234567890123456")
    private String numero;

    @Schema(example = "12/26")
    private String expiracao;

    @Schema(example = "123")
    private String codigo;

    @Schema(example = "CRIADO")
    private Status status;

    @Schema(example = "1")
    private Long formaDePagamentoId;

    @Schema(example = "1")
    private Long pedidoId;


}
