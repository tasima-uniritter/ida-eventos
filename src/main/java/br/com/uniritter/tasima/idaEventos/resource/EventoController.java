package br.com.uniritter.tasima.idaEventos.resource;

import br.com.uniritter.tasima.idaEventos.domain.model.Evento;
import br.com.uniritter.tasima.idaEventos.domain.repository.EventoDAO;
import br.com.uniritter.tasima.idaEventos.domain.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;

@RestController
@EnableAutoConfiguration
public class EventoController {

    @Autowired
    private EventoService eventoService;
//    GET eventos

    @RequestMapping("/evento") //POST
    public Evento evento(@RequestParam(value="name", defaultValue="World") String name) {
        Evento evento = new Evento();
        evento.setNome("Evento : "+Math.random());
        evento.setData(Calendar.getInstance().getTime());

        eventoService.create(evento);
        return evento;
    }
}
