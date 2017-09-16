package br.com.uniritter.tasima.idaEventos.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by Alu201730204 on 01/09/2017.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="EVENTO")
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID_EVENTO")
    private Long idEvento;

    @NotNull
    @Size(min = 1, max = 150, message = "erro.evento.nome.tamanho")
    @Column(name="NOME", unique = true)
    private String nome;

    @NotNull
    @Future(message = "erro.evento.data.futuro")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Column(name="DATA")
    private Date data;
}
