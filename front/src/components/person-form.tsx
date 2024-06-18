import React, { useState } from 'react';
import { Header } from './header';
import { Button, useToast } from '@chakra-ui/react';
import { personService } from '../services/personService';
import { Contacts } from '../interface/contacts';
import { isValid } from '../utils/EmailUtils';
import InputMask from 'react-input-mask';
import { isPhoneValid } from '../utils/PhoneUtils';
import { Person } from '../interface/person';


export const PersonForm = () => {
    const toast = useToast();
    const formRef = React.useRef<HTMLFormElement>(null);
    const [contacts, setContacts] = useState<Array<Contacts>>([]);
    const [person, setPerson] = useState<Person>({
        name: '',
        cpf: '',
        birthDate: '',
        contacts: [],
    });
    const [name, setName] = useState('');
    const [cpf, setCpf] = useState('');
    const [birthDate, setBirthDate] = useState('');
    const [contact, setContact] = useState<Contacts>({ name: '', phone: 0, email: '' });

    const handleNameChange = (e: { target: { value: React.SetStateAction<string>; }; }) => {
        setName(e.target.value);
    };

    const handleCpfChange = (e: { target: { value: React.SetStateAction<string>; }; }) => {
        setCpf(e.target.value);
    };

    const handleBirthDateChange = (e: { target: { value: React.SetStateAction<string>; }; }) => {
        setBirthDate(e.target.value);
    };

    const handleContactNameChange = (e: { target: { value: any; }; }) => {
        setContact({ ...contact, name: e.target.value });
    };

    const handleContactPhoneChange = (e: { target: { value: any; }; }) => {
        setContact({ ...contact, phone: new Number(e.target.value) as number });
    };

    const handleContactEmailChange = (e: { target: { value: any; }; }) => {
        setContact({ ...contact, email: e.target.value});
    };

    function handleAddContact(event: React.FormEvent) {
        event.preventDefault();
        const form = event.target as HTMLFormElement;
        const formData = new FormData(form);
        validatePhone(new Number(formData.get('phone')));
        validateEmail(formData.get('email') as string);
        const newContact ={
            name: formData.get('name') as string,
            phone: new Number(formData.get('phone')) as number,
            email: formData.get('email') as string
        };
        setContacts([...contacts, newContact]);
    }

    function handleSubmit(event: { preventDefault: () => void; }) {
        event.preventDefault();
        const formData = new FormData(formRef.current!);
        validateBirthDate(new Date(formData.get('birthDate') as string), new Date());
        validateCPF(formData.get('cpf') as string);
        validateContacts(contacts);
        const newPerson = {
            name: formData.get('name') as string,
            cpf: formData.get('cpf') as string,
            birthDate: new Date(formData.get('birthDate') as string).toISOString(),
            contacts: contacts
        };
        personService.createPerson(newPerson);
    }

    function validateBirthDate(birthDate: Date, currentDate: Date) {
        console.log(birthDate, currentDate);
        currentDate.setHours(0, 0, 0, 0); 
        if (birthDate > currentDate) {
            toast({
                title: "A data de nascimento não pode ser uma data futura.",
                status: "error",
                duration: 3000,
                isClosable: true,
            });
            return;
        }
    }
    function validateCPF(cpf: string) {
        if (cpf.length !== 11) {
            toast({
                title: "O CPF deve conter 11 dígitos.",
                status: "error",
                duration: 3000,
                isClosable: true,
            });
            return;
        }
        if (!cpf.match(/^d{3}.d{3}.d{3}-d{2}$/)) {
            toast({
                title: "O CPF inválido.",
                status: "error",
                duration: 3000,
                isClosable: true,
            });
            return;
        }
    }
    
    function validateEmail(email: string) {
        if (!isValid(email)) {
            toast({
                title: "O email informado é inválido. Por favor, preenchê-lo corretamente.",
                status: "error",
                duration: 3000,
                isClosable: true,
            });
            return;
        }
    }
    
    function validatePhone(phone: Number) {
        if (phone.toString().length < 10) {
            toast({
                title: "O telefone deve conter no mínimo 10 dígitos.",
                status: "error",
                duration: 3000,
                isClosable: true,
            });
            return;
        }
        if (!isPhoneValid(phone.toString())) {
            toast({
                title: "O telefone informado é inválido. Por favor, preenchê-lo corretamente.",
                status: "error",
                duration: 3000,
                isClosable: true,
            })
            return;
        }
    }
    
    function validateContacts(contacts: Contacts[]) {
        if (!contacts.length) {
            toast({
                title: "É necessário adicionar ao menos um contato.",
                status: "error",
                duration: 3000,
                isClosable: true,
            });
            return;
        }
    }

    return (
        <div>
            <Header />
            <div className="container mx-auto p-4 justify-between">
                <div className="flex flex-col items-center">
                    <h2 className="font-bold text-2xl mb-4">Formulário de Pessoa</h2>
                    <form ref={formRef} onSubmit={handleSubmit} className="person w-full max-w-sm">
                        <div className="mb-4">
                            <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="name">
                                Nome:
                            </label>
                            <input className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" 
                                type="text" id="name" name="name" placeholder='ex: Ana' value={name} onChange={handleNameChange} />
                        </div>
                        <div className="mb-4">
                            <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="cpf">
                                CPF:
                            </label>
                            <input className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" 
                                type="text" id="cpf" name="cpf" placeholder='000.000.000-00' value={cpf} onChange={handleCpfChange} 
                            />
                        </div>
                        <div className="mb-4">
                            <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="birthDate">
                                Data de Nascimento:
                            </label>
                            <input className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" 
                                type="date" id="birthDate" name="birthDate" value={birthDate} onChange={handleBirthDateChange}
                                max={new Date().toISOString().split("T")[0]} placeholder='dd/mm/aaaa' />
                        </div>
                        {contacts.map((contact, index) => (
                            <div key={index} className="mb-4">
                                <label className="block text-gray-700 text-sm font-bold mb-2">
                                    {contact.name}:
                                </label>
                                <input className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" 
                                    type="text" value={`${contact}: ${contact.name}`} readOnly />
                            </div>
                        ))}
                        <div className="flex items-center justify-between">
                            <Button className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline" 
                                    onClick={handleSubmit} type="submit">
                                Salvar
                            </Button>
                        </div>
                    </form>
                </div>
                <div className="flex flex-col items-center">
                    <h2 className="font-bold text-2xl mb-4">Contatos</h2>
                    <form className="contacts w-full max-w-sm">
                        <div className="mb-4">
                            <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="name">
                                Nome:
                            </label>
                            <input className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" 
                                type="text" id="name" name="name" placeholder='ex: Ana' value={contact.name} onChange={handleContactNameChange} />
                        </div>
                        <div className="mb-4">
                            <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="phone">
                                Telefone:
                            </label>
                            <input className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" 
                                type="text" id="phone" name="phone" placeholder='(99) 99999-9999' value={contact.phone} onChange={handleContactPhoneChange}/>
                        </div>
                        <div className="mb-4">
                            <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="text">
                                Email:
                            </label>
                            <input className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" 
                                type="text" id="email" name="email" value={contact.email} onChange={handleContactEmailChange}/>
                        </div>
                        <div className="flex items-center justify-between">
                            <Button className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline" 
                                    onClick={handleAddContact} type="submit">
                                Adicionar Contato
                            </Button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    );
};

export default PersonForm;
