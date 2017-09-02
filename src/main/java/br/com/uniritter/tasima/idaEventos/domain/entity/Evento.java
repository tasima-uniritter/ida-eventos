package br.com.uniritter.tasima.idaEventos.domain.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Alu201730204 on 01/09/2017.
 */
public class Evento {
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
}
