package br.com.uniritter.tasima.idaEventos.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.uniritter.tasima.idaEventos.domain.model.Evento;
import br.com.uniritter.tasima.idaEventos.domain.repository.EventoRepository;

@Service
@Transactional
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    public void cadastrar(Evento evento) {
        eventoRepository.save(evento);
    }

    public List<Evento> listarTodos() {
        return eventoRepository.findAll();
    }

    public Evento buscarPorId(long id) {
        return eventoRepository.findOne(id);
    }

    public Evento buscarPorNome(String nome) {
        return eventoRepository.findByNome(nome);
    }

    public void deletar(long id) {
        eventoRepository.delete(id);
    }
}