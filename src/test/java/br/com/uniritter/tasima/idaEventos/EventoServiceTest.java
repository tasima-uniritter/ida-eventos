package br.com.uniritter.tasima.idaEventos;

import br.com.uniritter.tasima.idaEventos.domain.model.Evento;
import br.com.uniritter.tasima.idaEventos.domain.service.EventoService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class EventoServiceTest {

    @Autowired
    private EventoService eventoService;

    @Test
    public void retornar_idXPTO_evento() {
        Evento evento = eventoService.retornarPorId(1L);

        Assert.assertNotNull(evento);

    }

}
