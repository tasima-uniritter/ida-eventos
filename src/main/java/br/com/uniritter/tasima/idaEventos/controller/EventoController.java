package br.com.uniritter.tasima.idaEventos.controller;

import br.com.uniritter.tasima.idaEventos.domain.model.Evento;
import br.com.uniritter.tasima.idaEventos.domain.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@EnableAutoConfiguration
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @RequestMapping(method = RequestMethod.GET, value = "/Eventos")
    public ResponseEntity<List<Evento>> eventos() {

        return new ResponseEntity<>(eventoService.listarTodosEventos(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/CadastrarEvento")
    public ResponseEntity<?> cadastrarEvento(@RequestBody Evento evento) {
        eventoService.cadastrarEvento(evento);
        return new ResponseEntity<String>(HttpStatus.CREATED);
    }
}
