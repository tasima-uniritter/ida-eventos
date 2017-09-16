package br.com.uniritter.tasima.idaEventos.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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
    @Size(min = 1, max = 150, message = "error.nome.size")
    @Column(name="NOME", unique = true)
    private String nome;

    @NotNull
    @Column(name="DATA")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private Date data;
}
