package br.com.uniritter.tasima.idaEventos.domain.service;

import br.com.uniritter.tasima.idaEventos.domain.enums.CategoriaDesconto;
import br.com.uniritter.tasima.idaEventos.domain.model.Ingresso;
import br.com.uniritter.tasima.idaEventos.domain.model.factory.CalculadoraDescontoFactory;
import br.com.uniritter.tasima.idaEventos.domain.repository.IngressoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class IngressoService {

    @Autowired
    private IngressoRepository ingressoRepository;

    @Autowired
    private CalculadoraDescontoFactory calculadoraDescontoFactory;

    public void cadastrarIngresso(Ingresso ingresso) {
        ingressoRepository.save(ingresso);
    }

    public List<Ingresso> listarIngressos() {
        return ingressoRepository.findAll();
    }

    public Ingresso buscarPorTipo(String tipo) {
        return ingressoRepository.findByTipoIgnoreCase(tipo);
    }

    public Ingresso buscarPorId(long id) {
        return ingressoRepository.findOne(id);
    }

    public double calcularValorDoIngressoComDesconto(Ingresso ingresso, CategoriaDesconto categoriaDesconto) {
        return this.calculadoraDescontoFactory
                .getStrategy(categoriaDesconto.getCategoria())
                .calcularValorDesconto(ingresso.getValor());
    }
}