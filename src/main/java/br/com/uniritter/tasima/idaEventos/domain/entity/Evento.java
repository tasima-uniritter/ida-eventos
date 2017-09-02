package br.com.uniritter.tasima.idaEventos.domain.entity;

import br.com.uniritter.tasima.idaEventos.domain.service.EventoService;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Alu201730204 on 01/09/2017.
 */
public class Evento {

    private EventoService _eventoService;

    public Evento(String nomeEvento, Date dataDoEvento, EventoService eventoService) {
        nome = nomeEvento;
        data = dataDoEvento;
        _eventoService = eventoService;
    }

    private String nome;
    private Date data;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public boolean criarEvento() {
        return _eventoService.Salvar(this);
    }
}
