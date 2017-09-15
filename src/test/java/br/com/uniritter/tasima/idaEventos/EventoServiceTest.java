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
    	//dado um identificador aleatório
    	long idAleatorio = 43214231L;
    	
    	//quando um evento for buscado
        Evento evento = eventoService.buscar(idAleatorio);

        //então o evento deve ser nulo
        Assert.assertNull(evento);
    }
    
    @Test
    public void inserir_eventoValido_eventoInserido() {
    	//dado um evento populado
    	Evento evento = popularDto();
    	
    	//quando eu inserir o evento
    	eventoService.inserir(evento);
    	
//    	Evento eventoInserido = eventoService.buscar(evento.getId());
    	
    	//então o evento inserido não pode ser nulo.
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
    	
//    	evento.setId(123L);
//    	evento.setNome("Meu nome");
//    	evento.setDate(new Date());
    	
    	return evento;
    }
}
