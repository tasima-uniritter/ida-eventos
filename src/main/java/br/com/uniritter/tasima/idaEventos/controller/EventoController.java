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
@RequestMapping("/evento")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @RequestMapping(method = RequestMethod.GET, value = "/listar")
    public ResponseEntity<List<Evento>> listarEventos() {
        return new ResponseEntity<>(eventoService.listarTodosEventos(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/buscar/{id}")
    public ResponseEntity<List<Evento>> buscarPorNome(@PathVariable String nome) {
        Evento evento = eventoService.buscarPorNome(nome);
        if (evento != null) {
            return new ResponseEntity(evento, HttpStatus.OK);
        } else {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/id/{id}")
    public ResponseEntity<List<Evento>> buscarPorId(@PathVariable long id) {
        Evento evento = eventoService.buscarPorId(id);
        if (evento != null) {
            return new ResponseEntity(evento, HttpStatus.OK);
        } else {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/cadastrar")
    public ResponseEntity<?> cadastrarEvento(@RequestBody Evento evento) {
        eventoService.cadastrarEvento(evento);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
