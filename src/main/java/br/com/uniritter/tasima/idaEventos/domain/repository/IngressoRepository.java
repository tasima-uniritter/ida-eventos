package br.com.uniritter.tasima.idaEventos.domain.repository;

import br.com.uniritter.tasima.idaEventos.domain.model.Ingresso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngressoRepository extends JpaRepository<Ingresso, Long> {
    Ingresso findByTipoIgnoreCase(String tipo);
}