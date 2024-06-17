package back.projeto.contato.model.dto;

import lombok.Data;

@Data
public class ContactRequestDTO {
    private Long id;
    private String name;
    private String phone;
    private String email;
}
