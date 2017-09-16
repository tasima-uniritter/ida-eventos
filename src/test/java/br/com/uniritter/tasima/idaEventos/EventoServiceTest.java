package br.com.uniritter.tasima.idaEventos;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.uniritter.tasima.idaEventos.domain.model.Evento;
import br.com.uniritter.tasima.idaEventos.domain.service.EventoService;

@RunWith(SpringRunner.class)
public class EventoServiceTest {

    @Autowired
    private EventoService eventoService;

    @Test
    public void buscar_idInexistente_eventoInexistente() {
    	//dado um identificador aleat�rio
    	long idAleatorio = 43214231L;
    	
    	//quando um evento for buscado
        Evento evento = eventoService.buscarPorId(idAleatorio);

        //ent�o o evento deve ser nulo
        Assert.assertNull(evento);
    }
    
    @Test
    public void inserir_eventoValido_eventoInserido() {
    	//dado um evento populado
    	Evento evento = popularDto();
    	
    	//quando eu inserir o evento
    	eventoService.inserir(evento);
    	
//    	Evento eventoInserido = eventoService.buscar(evento.getId());
    	
    	//ent�o o evento inserido n�o pode ser nulo.
//  	Assert.assertNotNull(eventoInserido);
    }
    
    @Test
    public void atualizar_eventoExistente_success() {
    
    }
    
    @Test
    public void deletar_eventoExistente_success() {
    
    }
    
    private Evento popularDto() {
    	Evento evento = new Evento();
    	
    	evento.setIdEvento(123L);
    	evento.setNome("Fulano da Silva");
    	
    	return evento;
    }
}
