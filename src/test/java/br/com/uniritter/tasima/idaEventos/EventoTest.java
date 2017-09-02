package br.com.uniritter.tasima.idaEventos;

import br.com.uniritter.tasima.idaEventos.domain.entity.Evento;
import br.com.uniritter.tasima.idaEventos.domain.service.EventoService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;

import static org.mockito.ArgumentMatchers.booleanThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class EventoTest {

    private EventoService _eventoService;

    @Before
    public void Init() {
        _eventoService = mock(EventoService.class);
    }

	@Test
	public void testaNomeEvento() {
        Evento evento = new Evento("Novo do Evento", Calendar.getInstance().getTime(), _eventoService);

        Assert.assertEquals("Novo do Evento", evento.getNome());
    }

	@Test
	public void criarEvento(){
        Evento evento = new Evento("Nome do Evento", Calendar.getInstance().getTime(), _eventoService);

        when(_eventoService.Salvar(evento)).thenReturn(true);

        boolean resultado = evento.criarEvento();

        Assert.assertTrue(resultado);
    }

}
