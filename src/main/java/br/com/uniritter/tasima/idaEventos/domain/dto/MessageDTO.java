package br.com.uniritter.tasima.idaEventos.domain.dto;

import br.com.uniritter.tasima.idaEventos.domain.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {
    private MessageType type;
    private String message;
}
