package br.com.uniritter.tasima.idaEventos.controller;

import br.com.uniritter.tasima.idaEventos.domain.model.Ingresso;
import br.com.uniritter.tasima.idaEventos.domain.service.IngressoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@EnableAutoConfiguration
@RequestMapping("/ingresso")
public class IngressoController {

    @Autowired
    private IngressoService ingressoService;

    @RequestMapping(method = RequestMethod.GET, value = "/listar")
    public ResponseEntity<List<Ingresso>> listar() {
        return new ResponseEntity<>(ingressoService.listarIngresso(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/cadastrar")
    public ResponseEntity<?> cadastrar(@RequestBody Ingresso ingresso) {
        ingressoService.cadastrarIngresso(ingresso);
        return new ResponseEntity<String>(HttpStatus.CREATED);
    }
}
