package br.com.uniritter.tasima.idaEventos.domain.repository;

import br.com.uniritter.tasima.idaEventos.domain.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepository extends JpaRepository<Evento, Long> {
}