package br.com.alurafood.pagamentos.service;

import br.com.alurafood.pagamentos.dto.PagamentoDto;
import br.com.alurafood.pagamentos.model.Pagamento;
import br.com.alurafood.pagamentos.repository.PagamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import br.com.alurafood.pagamentos.http.PedidoClient;
import br.com.alurafood.pagamentos.model.Status;
import org.springframework.stereotype.Service;

@Service
public class PagamentoService {

    private final PagamentoRepository repository;
    private final ModelMapper modelMapper;
    private final PedidoClient pedido;

    public PagamentoService(PagamentoRepository repository, ModelMapper modelMapper, PedidoClient pedido) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.pedido = pedido;
    }

    public Page<PagamentoDto> obterTodos(Pageable paginacao) {
        try {
            return repository
                    .findAll(paginacao)
                    .map(p -> modelMapper.map(p, PagamentoDto.class));
        } catch (Exception e) {
            System.err.println("ERRO AO LISTAR: Verifique se o campo 'sort' no Swagger está vazio! Erro: " + e.getMessage());
            throw e;
        }
    }

    public PagamentoDto obterPorId(Long id) {
        Pagamento pagamento = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

        return modelMapper.map(pagamento, PagamentoDto.class);
    }

    public PagamentoDto criarPagamento(PagamentoDto dto) {
        Pagamento pagamento = modelMapper.map(dto, Pagamento.class);
        pagamento.setId(null);
        pagamento.setStatus(Status.CRIADO);

        Pagamento salvo = repository.save(pagamento);

        return modelMapper.map(salvo, PagamentoDto.class);
    }

    public PagamentoDto atualizarPagamento(Long id, PagamentoDto dto) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException();
        }

        Pagamento pagamento = modelMapper.map(dto, Pagamento.class);
        pagamento.setId(id);

        pagamento = repository.save(pagamento);

        return modelMapper.map(pagamento, PagamentoDto.class);
    }

    public void excluirPagamento(Long id) {
        repository.deleteById(id);
    }

    public void confirmarPagamento(Long id){
        Pagamento pagamento = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

        pagamento.setStatus(Status.CONFIRMADO);
        repository.save(pagamento);
        pedido.atualizaPagamento(pagamento.getPedidoId());
    }
}