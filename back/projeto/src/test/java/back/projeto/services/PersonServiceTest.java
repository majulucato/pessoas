package back.projeto.services;

import back.projeto.contato.model.Contact;
import back.projeto.contato.model.dto.ContactRequestDTO;
import back.projeto.pessoa.model.Person;
import back.projeto.pessoa.model.dto.PersonRequestDTO;
import back.projeto.pessoa.repository.PersonRepository;
import back.projeto.pessoa.service.PersonService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.mockito.ArgumentMatchers.any;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {
    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    @Nested
    class createPerson {
        @Test
        @DisplayName("Criando a pessoa com sucesso")
        void createPersonWithSuccess() throws ParseException {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Date date = formatter.parse("01-01-1999");
            var person = new PersonRequestDTO(
                    null,
                    "Maria",
                    "79950778077",
                    date,
                    new ArrayList<>()
            );
            var contacts = person.getContacts();
            contacts.add(
                new ContactRequestDTO(
                    null,
                    "Alana",
                    "123456789",
                    "alana@teste.com"));
            var savedPerson = new Person(
                    1L,
                    "Maria",
                    "79950778077",
                    date,
                    List.of(new Contact(
                            1L,
                            "Alana",
                            "alana@teste.com",
                            "123456789",
                            new Person()
                    ))
            );
            doReturn(savedPerson).when(personService).saveOrUpdate(any(PersonRequestDTO.class));
            var output = personService.saveOrUpdate(person);

            assertEquals(1L, output.getId());
            assertEquals("Maria", output.getName());
            assertEquals("79950778077", output.getCpf());
            assertEquals(date, output.getBirthDate());
            assertEquals(1, output.getContacts().size());
        }

        @Test
        @DisplayName("Apresetará erroao cadastrar Person com CPF inválido")
        void createPersonWithInvalidCpf() throws ParseException {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Date date = formatter.parse("01-01-2001");
            doThrow(new RuntimeException("Invalid CPF")).when(personRepository).save(any(Person.class));
            var person = new PersonRequestDTO(
                    null,
                    "Maria",
                    "000.000.000-00",
                    date,
                    new ArrayList<>()
            );
            person.getContacts().add(new ContactRequestDTO(
                    null,
                    "Alana",
                    "123456789",
                    "alana@teste.com"
            ));
            assertThrows(RuntimeException.class, () -> personService.saveOrUpdate(person));
        }

        @Test
        @DisplayName("lançar exceção ao tentar criar uma Person sem Contatos")
        void createPersonWithoutContactList() throws ParseException {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Date date = formatter.parse("03-03-2003");
            var newPerson = new PersonRequestDTO(
                    null,
                    "Amanda",
                    "12553169000",
                    date,
                    null
            );
            assertThrows(RuntimeException.class, () -> personService.saveOrUpdate(newPerson));
        }

    }

    @Nested
    class updatePerson {
        @Test
        @DisplayName("Atualziar com sucesso a Person")
        void updatePersonWithSucces() throws ParseException {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Date date = formatter.parse("04-04-2004");
            var newPerson = new PersonRequestDTO(
                    null,
                    "Bianca",
                    "32444168020",
                    date,
                    new ArrayList<>()
            );
            newPerson.getContacts().add(new ContactRequestDTO(
                    null,
                    "Maria",
                    "123456789",
                    "maria@gmail.com"
            ));

            var savedPerson = new Person(
                    1L,
                    "Bianca",
                    "32444168020",
                    date,
                    List.of(new Contact(
                            1L,
                            "Maria",
                            "maria@gmail.com",
                            "123456789",
                            new Person()
                    ))
            );
            doReturn(savedPerson).when(personService).saveOrUpdate(any(PersonRequestDTO.class));
            doReturn(Optional.of(savedPerson)).when(personRepository).findById(any(Long.class));

            var updatedPerson = new PersonRequestDTO(
                    1L,
                    "Bianca Silva",
                    "32444168020",
                    date,
                    List.of(new ContactRequestDTO(
                            1L,
                            "Maria",
                            "maria@gmail.com",
                            "123456789"
                    ))
            );
            var updatedOutput = personService.saveOrUpdate(updatedPerson);

            assertEquals(1L, updatedOutput.getId());
            assertEquals("Bianca Silva", updatedOutput.getName());
            assertEquals("32444168020", updatedOutput.getCpf());
            assertEquals(date, updatedOutput.getBirthDate());
            assertEquals(1, updatedOutput.getContacts().size());
        }

        @Test
        @DisplayName("Lançar exceção ao atualziar pessoa com cpf invalido")
        void updatePersonWithInvalidId() throws ParseException {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Date date = formatter.parse("05-05-2005");
            var newPerson = new PersonRequestDTO(
                    null,
                    "Bianca",
                    "32444168020",
                    date,
                    new ArrayList<>()
            );
            newPerson.getContacts().add(new ContactRequestDTO(
                    null,
                    "Maria",
                    "123456789",
                    "maria@gmail.com"
            ));

            var savedPerson = new Person(
                    1L,
                    "Bianca",
                    "32444168020",
                    date,
                    List.of(new Contact(
                            1L,
                            "Maria",
                            "maria@gmail.com",
                            "123456789",
                            new Person()
                    ))
            );
            doReturn(savedPerson).when(personService).saveOrUpdate(any(PersonRequestDTO.class));
            doReturn(Optional.of(savedPerson)).when(personRepository).findById(any(Long.class));

            var updatedPerson = new PersonRequestDTO(
                    1L,
                    "Bianca Silva",
                    null,
                    date,
                    List.of(new ContactRequestDTO(
                            1L,
                            "Maria",
                            "maria@gmail.com",
                            "123456789"
                    ))
            );
            assertThrows(RuntimeException.class, () -> personService.saveOrUpdate(updatedPerson));
        }
    }

    @Nested
    class deletePerson {
        @Test
        @DisplayName("Deletar person com sucesso")
        void deletePersonWithSuccess() throws ParseException {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Date date = formatter.parse("06-06-2006");
            var newPerson = new PersonRequestDTO(
                    null,
                    "Bianca",
                    "32444168020",
                    date,
                    new ArrayList<>()
            );
            newPerson.getContacts().add(new ContactRequestDTO(
                    null,
                    "Maria",
                    "123456789",
                    "maria@gmail.com"
            ));

            var savedPerson = new Person(
                    1L,
                    "Bianca",
                    "32444168020",
                    date,
                    List.of(new Contact(
                            1L,
                            "Maria",
                            "maria@gmail.com",
                            "123456789",
                            new Person()
                    ))
            );
            doReturn(savedPerson).when(personService).saveOrUpdate(any(PersonRequestDTO.class));

            personService.deleteById(savedPerson.getId());

            verify(personRepository, times(1)).deleteById(savedPerson.getId());
        }
    }

    @Nested
    class getPerson {

        @Test
        @DisplayName("Apresentar todos os valores")
        void getAllPersons() throws ParseException {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Date date = formatter.parse("07-07-2007");
            var newPerson = new PersonRequestDTO(
                    null,
                    "Bianca",
                    "32444168020",
                    date,
                    new ArrayList<>()
            );
            newPerson.getContacts().add(new ContactRequestDTO(
                    null,
                    "Maria",
                    "123456789",
                    "maria@gmail.com"
            ));

            var savedPerson = new Person(
                    1L,
                    "Bianca",
                    "32444168020",
                    date,
                    List.of(new Contact(
                            1L,
                            "Maria",
                            "maria@gmail.com",
                            "123456789",
                            new Person()
                    ))
            );
            doReturn(savedPerson).when(personService).saveOrUpdate(any(PersonRequestDTO.class));

            var savedPerson2 = new Person(
                    2L,
                    "Gabriela",
                    "15103728060",
                    date,
                    List.of(new Contact(
                            2L,
                            "Julia",
                            "ju@teste.com",
                            "987654321",
                            new Person()
                    ))
            );

            List<Person> persons = List.of(savedPerson, savedPerson2);
            Page<Person> page = new PageImpl<>(persons);

            when(personRepository.findAll(any(Pageable.class))).thenReturn(page);

            Pageable pageable = PageRequest.of(0, 2);
            Page<Person> result = personService.getAll(pageable);

            verify(personRepository, times(1)).findAll(pageable);
            assertEquals(2, result.getContent().size());
            assertEquals(savedPerson, result.getContent().get(0));
            assertEquals(savedPerson2, result.getContent().get(1));
        }

        @Test
        @DisplayName("Retronará nulo quando não encontrar a pessoa")
        void getPersonWithInvalidId() {
            doReturn(Optional.empty()).when(personRepository).findById(any(Long.class));
            Person result = personRepository.getById(999L);
            assertNull(result);
        }
    }

}