package br.com.uniritter.tasima.idaEventos.domain.service;

import br.com.uniritter.tasima.idaEventos.domain.model.Evento;
import br.com.uniritter.tasima.idaEventos.domain.repository.EventoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EventoService {

    @Autowired
    private EventoDAO eventoDAO;

    public void create(Evento evento) {
        eventoDAO.create(evento);
    }

    public Evento retornarPorId(Long id) {
        return new Evento();
    }
}