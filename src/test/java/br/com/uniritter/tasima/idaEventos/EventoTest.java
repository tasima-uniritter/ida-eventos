package br.com.uniritter.tasima.idaEventos;

import br.com.uniritter.tasima.idaEventos.domain.model.Evento;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;

@RunWith(SpringRunner.class)
public class EventoTest {

	@Test
	public void testaNomeEvento() {
		Evento evento = new Evento();
		evento.setData(Calendar.getInstance().getTime());
		evento.setNome("Novo Evento");

		Assert.assertEquals("Novo Evento", evento.getNome());
	}

	@Test
	public void criarEvento(){

	}

}
