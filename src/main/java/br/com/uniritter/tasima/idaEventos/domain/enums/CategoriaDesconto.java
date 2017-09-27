package br.com.uniritter.tasima.idaEventos.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CategoriaDesconto {
    ESTUDANTE("estudante"),
    IDOSO("idoso"),
    MEMBRO_GOLD("membroGold"),
    MEMBRO_SILVER("membroSilver"),
    PUBLICO_GERAL("publicoGeral");

    private final String categoria;
}
