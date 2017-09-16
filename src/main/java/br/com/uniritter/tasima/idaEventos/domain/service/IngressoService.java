package br.com.uniritter.tasima.idaEventos.domain.service;

import br.com.uniritter.tasima.idaEventos.domain.model.Ingresso;
import br.com.uniritter.tasima.idaEventos.domain.repository.IngressoRepository;
import com.sun.imageio.plugins.common.I18N;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class IngressoService {

    @Autowired
    private IngressoRepository ingressoRepository;

    public void cadastrarIngresso(Ingresso ingresso) {
        ingressoRepository.save(ingresso);
    }

    public List<Ingresso> listarIngresso() {
        return ingressoRepository.findAll();
    }

    public Ingresso buscarPorTipo(String t) {
        return ingressoRepository.findByTipo(t);
    }

    public Ingresso buscarPorId(long l) {
        return ingressoRepository.findOne(l);
    }

}