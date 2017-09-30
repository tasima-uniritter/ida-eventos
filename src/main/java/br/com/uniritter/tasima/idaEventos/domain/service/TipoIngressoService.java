package br.com.uniritter.tasima.idaEventos.domain.service;

import br.com.uniritter.tasima.idaEventos.domain.enums.CategoriaDesconto;
import br.com.uniritter.tasima.idaEventos.domain.model.TipoIngresso;
import br.com.uniritter.tasima.idaEventos.domain.model.factory.CalculadoraDescontoFactory;
import br.com.uniritter.tasima.idaEventos.domain.repository.TipoIngressoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TipoIngressoService {

    @Autowired
    private TipoIngressoRepository tipoIngressoRepository;

    @Autowired
    private CalculadoraDescontoFactory calculadoraDescontoFactory;

    public void cadastrarIngresso(TipoIngresso tipoIngresso) {
        tipoIngressoRepository.save(tipoIngresso);
    }

    public List<TipoIngresso> listarIngressos() {
        return tipoIngressoRepository.findAll();
    }

    public TipoIngresso buscarPorTipo(String tipo) {
        return tipoIngressoRepository.findByTipoIgnoreCase(tipo);
    }

    public TipoIngresso buscarPorId(long id) {
        return tipoIngressoRepository.findOne(id);
    }

    public void deletar(long id) {
        tipoIngressoRepository.delete(id);
    }

    public double calcularValorDoIngressoComDesconto(TipoIngresso tipoIngresso, CategoriaDesconto categoriaDesconto) {
        return this.calculadoraDescontoFactory
                .getStrategy(categoriaDesconto.getCategoria())
                .calcularValorDesconto(tipoIngresso.getValor());
    }
}