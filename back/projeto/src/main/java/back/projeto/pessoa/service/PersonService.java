package back.projeto.pessoa.service;

import back.projeto.contato.model.Contact;
import back.projeto.contato.service.ContactService;
import back.projeto.pessoa.model.Person;
import back.projeto.pessoa.model.dto.PersonRequestDTO;
import back.projeto.pessoa.repository.PersonRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final ContactService contactService;

    public PersonService(PersonRepository personRepository,
                         ContactService contactService) {
        this.personRepository = personRepository;
        this.contactService = contactService;
    }

    public Optional<Person> getById(Long id){
        return this.personRepository.findById(id);
    }

    public Page<Person> getAll(Pageable pageable) {
        return this.personRepository.findAll(pageable);
    }

    public Person saveOrUpdate(PersonRequestDTO requestDTO) {
        try {
            this.validateValues(requestDTO);
            this.correctValues(requestDTO);
            Person person = this.findOrCreatePerson(requestDTO);
            this.createContactsList(person, requestDTO);
            return this.personRepository.save(person);
        } catch (Exception e){
            throw new RuntimeException("Erro ao salvar pessoa: " + e.getMessage(), e);
        }
    }

    private void validateValues(PersonRequestDTO person) {
        if(Objects.isNull(person.getName())){
            throw new IllegalArgumentException("Nome não informado");
        }
        if (Objects.isNull(person.getCpf())){
            throw new IllegalArgumentException("CPF não informado");
        }
        if (this.personRepository.existsByCPF(person.getCpf().replaceAll("[^0-9]", ""))){
            throw new IllegalArgumentException("CPF já cadastrado");
        }
        if (Objects.isNull(person.getBirthDate())){
            throw new IllegalArgumentException("Data de nascimento não informada");
        }
        if  (person.getBirthDate().after(new Date())){
            throw new IllegalArgumentException("Data de nascimento inválida");
        }
        if (Objects.isNull(person.getContacts()) || person.getContacts().isEmpty()){
            throw new IllegalArgumentException("Contatos não informados");
        }
    }

    private void correctValues(@NotNull PersonRequestDTO person) {
        person.setCpf(person.getCpf().replaceAll("[^0-9]", ""));
        person.getContacts().forEach(contact -> {
            contact.setPhone(contact.getPhone().replaceAll("[^0-9]", ""));
        });
    }

    private Person findOrCreatePerson(PersonRequestDTO requestDTO) {
        if (Objects.isNull(requestDTO.getId())){
            return this.createPerson(requestDTO);
        } else {
            return this.updatePerson(requestDTO);
        }
    }

    private Person createPerson(PersonRequestDTO requestDTO) {
        return this.personRepository.save(Person.builder()
                .name(requestDTO.getName())
                .cpf(requestDTO.getCpf())
                .birthDate(requestDTO.getBirthDate())
                .contacts(new ArrayList<>())
                .build());
    }

    private Person updatePerson(PersonRequestDTO requestDTO) {
        Person person = this.personRepository.findById(requestDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada"));
        person.setName(requestDTO.getName());
        person.setCpf(requestDTO.getCpf());
        person.setBirthDate(requestDTO.getBirthDate());
        return person;
    }

    private void createContactsList(Person person, PersonRequestDTO requestDTO) {
        List<Contact> contactList = this.contactService.createContactsObject(person, requestDTO.getContacts());
        person.getContacts().retainAll(contactList);
        person.getContacts().addAll(contactList.stream().filter(contact -> !person.getContacts().contains(contact)).toList());
    }

    public void deleteById(Long id) {
        this.personRepository.deleteById(id);
    }

    public void deleteInBatchByIds(List<Long> ids) {
        this.personRepository.deleteAllByIdInBatch(ids);
    }
}
