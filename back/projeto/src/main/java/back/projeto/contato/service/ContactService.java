package back.projeto.contato.service;

import back.projeto.contato.model.Contact;
import back.projeto.contato.model.dto.ContactRequestDTO;
import back.projeto.contato.repository.ContactRepository;
import back.projeto.pessoa.model.Person;
import back.projeto.utils.EmailsUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ContactService {
    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public List<Contact> createContactsObject(Person person, List<ContactRequestDTO> contacts) {
        this.validateContacts(contacts);
        return contacts.stream().map(contactDTO -> {
            if (Objects.nonNull(contactDTO.getId())) {
                return this.updateContact(contactDTO);
            } else {
                return this.createNewObject(person, contactDTO);
            }
        }).toList();
    }

    private void validateContacts(List<ContactRequestDTO> contacts) {
        contacts.forEach(contact -> {
            if (Objects.isNull(contact.getName())) {
                throw new IllegalArgumentException("Nome do contato não informado");
            }
            if (Objects.isNull(contact.getPhone())) {
                throw new IllegalArgumentException("Telefone do contato não informado");
            }
            if (Objects.isNull(contact.getEmail())) {
                throw new IllegalArgumentException("Email do contato não informado");
            }
            if (!EmailsUtils.isValid(contact.getEmail())) {
                throw new IllegalArgumentException("Email do contato inválido");
            }
        });
    }

    public Contact updateContact(ContactRequestDTO contactDTO) {
        Contact contact = this.contactRepository.findById(contactDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Contato não encontrado"));
        contact.setName(contactDTO.getName());
        contact.setEmail(contactDTO.getEmail());
        contact.setPhone(contactDTO.getPhone());
        return this.contactRepository.save(contact);
    }

    public Contact createNewObject(Person person, ContactRequestDTO contactDTO) {
        return this.contactRepository.save(Contact.builder()
                .person(person)
                .name(contactDTO.getName())
                .phone(contactDTO.getPhone())
                .email(contactDTO.getEmail())
                .build());
    }

    public Contact saveNewContact(Contact newContact) {
        return this.contactRepository.save(newContact);
    }
}
