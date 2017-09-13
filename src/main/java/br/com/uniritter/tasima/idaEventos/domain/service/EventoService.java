package br.com.uniritter.tasima.idaEventos.domain.service;

import br.com.uniritter.tasima.idaEventos.domain.model.Evento;
import br.com.uniritter.tasima.idaEventos.domain.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    public void cadastrarEvento(Evento evento) {
        eventoRepository.save(evento);
    }

    public List<Evento> listarTodosEventos() {
        return eventoRepository.findAll();
    }
}