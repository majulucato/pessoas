package back.projeto.pessoa.model.dto;

import back.projeto.contato.model.dto.ContactRequestDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class PersonRequestDTO {
    private Long id;
    private String name;
    private String cpf;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date birthDate;
    private List<ContactRequestDTO> contacts;
}
