package br.com.uniritter.tasima.idaEventos.domain.repository;

import br.com.uniritter.tasima.idaEventos.domain.model.Evento;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class EventoDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public void create(Evento Evento) {
        entityManager.persist(Evento);
    }

    public void update(Evento Evento) {
        entityManager.merge(Evento);
    }

    public Evento getEventoById(long id) {
        return entityManager.find(Evento.class, id);
    }

    public void delete(long id) {
        Evento Evento = getEventoById(id);
        if (Evento != null) {
            entityManager.remove(Evento);
        }
    }
}