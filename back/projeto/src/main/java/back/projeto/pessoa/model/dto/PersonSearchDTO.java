package back.projeto.pessoa.model.dto;

import org.springframework.data.domain.Sort;

public record PersonSearchDTO(
    Integer page,
    Integer pageSize,
    String sort,
    Sort.Direction sortDir,
    String searchParam){
}
