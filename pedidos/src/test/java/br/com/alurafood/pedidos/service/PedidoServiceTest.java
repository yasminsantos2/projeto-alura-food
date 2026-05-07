package br.com.alurafood.pedidos.service;

import br.com.alurafood.pedidos.dto.PedidoDto;
import br.com.alurafood.pedidos.model.Pedido;
import br.com.alurafood.pedidos.model.Status;
import br.com.alurafood.pedidos.repository.PedidoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private PedidoRepository repository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PedidoService service;

    @Test
    void deveriaCriarUmPedidoComStatusRealizado() {
        PedidoDto dto = new PedidoDto();
        Pedido pedido = new Pedido();

        Mockito.when(modelMapper.map(dto, Pedido.class)).thenReturn(pedido);
        Mockito.when(repository.save(any(Pedido.class))).thenReturn(pedido);
        Mockito.when(modelMapper.map(pedido, PedidoDto.class)).thenReturn(dto);

        PedidoDto resultado = service.criarPedido(dto);

        Assertions.assertEquals(Status.REALIZADO, pedido.getStatus());
        Assertions.assertNotNull(resultado);
        Mockito.verify(repository).save(pedido);
    }

    @Test
    void deveriaLancarExcecaoAoBuscarIdInexistente() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> service.obterPorId(1L));
    }

    @Test
    void deveriaAprovarPagamentoDeUmPedido() {
        Pedido pedido = new Pedido();
        pedido.setStatus(Status.REALIZADO);

        Mockito.when(repository.porIdComItens(1L)).thenReturn(pedido);

        service.aprovaPagamentoPedido(1L);

        Assertions.assertEquals(Status.PAGO, pedido.getStatus());
        Mockito.verify(repository).save(pedido);
    }
}
