package back.projeto.pessoa.service;

import back.projeto.pessoa.model.Person;
import back.projeto.pessoa.model.dto.PersonSearchDTO;
import back.projeto.pessoa.repository.PersonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonService {
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Optional<Person> getById(Long id){
        return this.personRepository.findById(id);
    }

    public Page<Person> getAll(PersonSearchDTO searchDTO) {
        PageRequest pageRequest = PageRequest.of(searchDTO.page(), searchDTO.pageSize(), searchDTO.sortDir(), searchDTO.sort());
        return this.personRepository.findAll(pageRequest);
    }
}
