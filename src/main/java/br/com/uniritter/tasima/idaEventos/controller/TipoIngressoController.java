package br.com.uniritter.tasima.idaEventos.controller;

import br.com.uniritter.tasima.idaEventos.domain.enums.CategoriaDesconto;
import br.com.uniritter.tasima.idaEventos.domain.model.TipoIngresso;
import br.com.uniritter.tasima.idaEventos.domain.service.TipoIngressoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@EnableAutoConfiguration
@RequestMapping("/tipoIngresso")
public class TipoIngressoController {

    @Autowired
    private TipoIngressoService tipoIngressoService;

    @RequestMapping(method = RequestMethod.POST, value = "/cadastrar")
    public ResponseEntity<?> cadastrar(@RequestBody TipoIngresso tipoIngresso) {
        tipoIngressoService.cadastrarIngresso(tipoIngresso);
        return new ResponseEntity<String>(HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/listar")
    public ResponseEntity<List<TipoIngresso>> listar() {
        return new ResponseEntity<>(tipoIngressoService.listarIngressos(), HttpStatus.OK);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(method = RequestMethod.GET, value = "/buscar/{tipo}")
    public ResponseEntity<TipoIngresso> buscarPorTipo(@PathVariable String tipo) {
        TipoIngresso tipoIngresso = tipoIngressoService.buscarPorTipo(tipo);
        if (tipoIngresso != null) {
            return new ResponseEntity(tipoIngresso, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/deletar/{id}")
    public ResponseEntity<?> deletar(@PathVariable long id) {
        tipoIngressoService.deletar(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(method = RequestMethod.GET, value = "/id/{id}")
    public ResponseEntity<TipoIngresso> buscarPorId(@PathVariable long id) {
        TipoIngresso tipoIngresso = tipoIngressoService.buscarPorId(id);
        if (tipoIngresso != null) {
            return new ResponseEntity(tipoIngresso, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(method = RequestMethod.GET, value = "/calcularValor/{tipoIngresso}/{categoriaDesconto}")
    public ResponseEntity<Double> calcularValorDoIngressoComDesconto(@PathVariable String tipoIngresso, @PathVariable CategoriaDesconto categoriaDesconto) {
        TipoIngresso ingresso = tipoIngressoService.buscarPorTipo(tipoIngresso);
        if (ingresso != null) {
            double valorDoIngressoComDesconto = tipoIngressoService.calcularValorDoIngressoComDesconto(ingresso, categoriaDesconto);

            return new ResponseEntity(valorDoIngressoComDesconto, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}