package br.com.uniritter.tasima.idaEventos.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Alu201730204 on 01/09/2017.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
@Table(name="EVENTO")
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID_EVENTO")
    private Long idEvento;

    @Column(name="NOME")
    private String nome;

    @Column(name="DATA")
    private Date data;
}
