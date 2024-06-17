package back.projeto.contato.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContactRequestDTO {
    private Long id;
    private String name;
    private String phone;
    private String email;
}
