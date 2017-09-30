package br.com.uniritter.tasima.idaEventos.controller;

import br.com.uniritter.tasima.idaEventos.domain.enums.CategoriaDesconto;
import br.com.uniritter.tasima.idaEventos.domain.model.Ingresso;
import br.com.uniritter.tasima.idaEventos.domain.service.IngressoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@EnableAutoConfiguration
@RequestMapping("/ingresso")
public class IngressoController {

    @Autowired
    private IngressoService ingressoService;

    @RequestMapping(method = RequestMethod.POST, value = "/cadastrar")
    public ResponseEntity<?> cadastrar(@RequestBody Ingresso ingresso) {
        ingressoService.cadastrarIngresso(ingresso);
        return new ResponseEntity<String>(HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/listar")
    public ResponseEntity<List<Ingresso>> listar() {
        return new ResponseEntity<>(ingressoService.listarIngressos(), HttpStatus.OK);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(method = RequestMethod.GET, value = "/buscar/{tipo}")
    public ResponseEntity<Ingresso> buscarPorTipo(@PathVariable String tipo) {
        Ingresso ingresso = ingressoService.buscarPorTipo(tipo);
        if (ingresso != null) {
            return new ResponseEntity(ingresso, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/deletar/{id}")
    public ResponseEntity<?> deletar(@PathVariable long id) {
        ingressoService.deletar(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(method = RequestMethod.GET, value = "/id/{id}")
    public ResponseEntity<Ingresso> buscarPorId(@PathVariable long id) {
        Ingresso ingresso = ingressoService.buscarPorId(id);
        if (ingresso != null) {
            return new ResponseEntity(ingresso, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(method = RequestMethod.GET, value = "/calcularValor/{tipoIngresso}/{categoriaDesconto}")
    public ResponseEntity<Double> calcularValorDoIngressoComDesconto(@PathVariable String tipoIngresso, @PathVariable CategoriaDesconto categoriaDesconto) {
        Ingresso ingresso = ingressoService.buscarPorTipo(tipoIngresso);
        if (ingresso != null) {
            double valorDoIngressoComDesconto = ingressoService.calcularValorDoIngressoComDesconto(ingresso, categoriaDesconto);

            return new ResponseEntity(valorDoIngressoComDesconto, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}