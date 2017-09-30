package br.com.uniritter.tasima.idaEventos.domain.repository;

import br.com.uniritter.tasima.idaEventos.domain.model.TipoIngresso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoIngressoRepository extends JpaRepository<TipoIngresso, Long> {
    TipoIngresso findByTipoIgnoreCase(String tipo);
}