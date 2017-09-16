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

    public void cadastrarEvento(Evento evento) {
        eventoRepository.save(evento);
    }

    public List<Evento> listarTodosEventos() {
        return eventoRepository.findAll();
    }

    public Evento buscarPorId(long l) {
        return eventoRepository.findOne(l);
    }

    public Evento buscarPorNome(String s) {
        return eventoRepository.findByNome(s);
    }

	public void inserir(Evento evento) {
	}
}