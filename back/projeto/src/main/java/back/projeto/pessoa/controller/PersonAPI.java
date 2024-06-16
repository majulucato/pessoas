package back.projeto.pessoa.controller;

import back.projeto.pessoa.model.Person;
import back.projeto.pessoa.model.dto.PersonSearchDTO;
import back.projeto.pessoa.service.PersonService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping(value = "/pessoa")
public class PersonAPI {
    public final PersonService personService;

    public PersonAPI(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Person>> getById(@PathVariable Long id){
        return ResponseEntity.ok(this.personService.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<Person>> getAll(PersonSearchDTO searchDTO){
        return ResponseEntity.ok(this.personService.getAll(searchDTO));
    }


}
