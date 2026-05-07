package br.com.alurafood.pagamentos.service;

import br.com.alurafood.pagamentos.dto.PagamentoDto;
import br.com.alurafood.pagamentos.http.PedidoClient;
import br.com.alurafood.pagamentos.model.Pagamento;
import br.com.alurafood.pagamentos.model.Status;
import br.com.alurafood.pagamentos.repository.PagamentoRepository;
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
class PagamentoServiceTest {

    @Mock
    private PagamentoRepository repository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PedidoClient pedidoClient;

    @InjectMocks
    private PagamentoService service;

    @Test
    void deveriaCriarUmPagamentoComStatusCriado() {
        PagamentoDto dto = new PagamentoDto();
        Pagamento pagamento = new Pagamento();

        Mockito.when(modelMapper.map(dto, Pagamento.class)).thenReturn(pagamento);
        Mockito.when(repository.save(any(Pagamento.class))).thenReturn(pagamento);
        Mockito.when(modelMapper.map(pagamento, PagamentoDto.class)).thenReturn(dto);

        PagamentoDto resultado = service.criarPagamento(dto);

        Assertions.assertEquals(Status.CRIADO, pagamento.getStatus());
        Assertions.assertNotNull(resultado);
        Mockito.verify(repository).save(pagamento);
    }

    @Test
    void deveriaConfirmarUmPagamentoENotificarPedido() {
        Pagamento pagamento = new Pagamento();
        pagamento.setId(1L);
        pagamento.setPedidoId(10L);
        pagamento.setStatus(Status.CRIADO);

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(pagamento));

        service.confirmarPagamento(1L);

        Assertions.assertEquals(Status.CONFIRMADO, pagamento.getStatus());
        Mockito.verify(repository).save(pagamento);
        Mockito.verify(pedidoClient).atualizaPagamento(10L);
    }

    @Test
    void deveriaLancarExcecaoAoConfirmarPagamentoInexistente() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> service.confirmarPagamento(1L));
    }
}
