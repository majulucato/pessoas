package back.projeto.pessoa.controller;

import back.projeto.pessoa.model.Person;
import back.projeto.pessoa.model.dto.PersonRequestDTO;
import back.projeto.pessoa.model.dto.PersonSearchDTO;
import back.projeto.pessoa.service.PersonService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @PostMapping
    public ResponseEntity<Person> save(@RequestBody PersonRequestDTO requestDTO){
        return ResponseEntity.ok(this.personService.saveOrUpdate(requestDTO));
    }

    @PutMapping
    public ResponseEntity<Person> update(@RequestBody PersonRequestDTO requestDTO){
        return ResponseEntity.ok(this.personService.saveOrUpdate(requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        this.personService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete-batch")
    public ResponseEntity<Void> deleteBatch(@RequestParam List<Long> ids){
        this.personService.deleteInBatchByIds(ids);
        return ResponseEntity.noContent().build();
    }
}
