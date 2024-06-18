package back.projeto.pessoa.controller;

import back.projeto.pessoa.model.Person;
import back.projeto.pessoa.model.dto.PersonRequestDTO;
import back.projeto.pessoa.service.PersonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(value = "/pessoa")
public class PersonAPI {
    public final PersonService personService;

    public PersonAPI(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/{id}")
    @CrossOrigin()
    public ResponseEntity<Optional<Person>> getById(@PathVariable Long id){
        return ResponseEntity.ok(this.personService.getById(id));
    }

    @GetMapping("/all")
    @CrossOrigin()
    public ResponseEntity<Page<Person>> getAll(@RequestParam int page,
                                               @RequestParam int size,
                                               @RequestParam String param){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "name"));
        return ResponseEntity.ok(this.personService.getAll(pageable, param));
    }

    @PostMapping
    @CrossOrigin()
    public ResponseEntity<Person> save(@RequestBody PersonRequestDTO requestDTO){
        return ResponseEntity.ok(this.personService.saveOrUpdate(requestDTO));
    }


    @PutMapping
    @CrossOrigin()
    public ResponseEntity<Person> update(@RequestBody PersonRequestDTO requestDTO){
        return ResponseEntity.ok(this.personService.saveOrUpdate(requestDTO));
    }

    @DeleteMapping("/{id}")
    @CrossOrigin()
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        this.personService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
