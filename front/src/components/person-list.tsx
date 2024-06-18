import { useState, useEffect } from 'react';
import personService from '../services/personService';
import { Person } from '../interface/person';
import { Header } from './header';
import ContactList from './contactsModal';
import PersonForm from './person-form';
import PersonEdit from './personEdit';

export const PersonList = () => {    
    const [personList, setPersonList] = useState<Person[]>([]);
    const [totalPages, setTotalPages] = useState(0);
    const [page, setPage] = useState(1);
    const [size, setSize] = useState(10);
    const [search, setSearch] = useState('');

    const handleDelete = (id: number | undefined) => {
        personService.deletePerson(id as number).then(() => {
            setPersonList(personList.filter((person) => person.id !== id));
        });
    };

    function  handleResponse(response: any) {
        console.log(response);
        setPage(response.page.number);
        setSize(response.page.size);
        setTotalPages(response.page.totalPages);
        setPersonList(response.content);
    }

    function formatCPF(cpf: string) {
        return cpf.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, "$1.$2.$3-$4");
    }

    function formatDate(date: string) {
        return new Date(date).toLocaleDateString('pt-BR');
    }

    function showContacts(person: Person) {
        return <ContactList {...person.contacts} />;
    }

    function openForms(person: Person) {
        return <PersonEdit {... person } />;
    }

    useEffect(() => {
        const getPersonList = async () => {
            try {
                await personService.getPersonList(page, size, search)
                .then((response) => handleResponse(response));
            } catch (error) {
                console.error('Erro ao buscar lista de pessoas:', error);
            }
        };
        getPersonList();
    }, []);

    return (            
        <div>
            <Header />
            <div className="container mx-auto px-4">
                <h2 className="text-2xl font-bold mb-5">Lista de Pessoas</h2>
                <table className="w-full text-md bg-white shadow-md rounded mb-4">
                    <thead>
                        <tr className="border-b">
                            <th className="text-left p-3 px-5" >Nome</th>
                            <th className="text-left p-3 px-5" >CPF</th>
                            <th className="text-left p-3 px-5" >Nascimento</th>
                            <th className="text-left p-3 px-5">Ações</th>
                        </tr>
                    </thead>
                    <tbody>
                        {personList?.map((person, index) => {
                            return (
                                <tr className="border-b hover:bg-orange-100" key={index}>
                                    <td className="p-3 px-5">{person.name}</td>
                                    <td className="p-3 px-5">{formatCPF(person.cpf)}</td>
                                    <td className="p-3 px-5">{formatDate(person.birthDate)}</td>
                                    <td className="p-3 px-5">
                                        <button onClick={() => showContacts(person)} className="mx-2 btn btn-sm btn-primary">Ver contatos</button>
                                        <button onClick={() => openForms(person)} className="mx-2 btn btn-sm btn-primary">Editar</button>
                                        <button onClick={() => handleDelete(person.id)} className="mx-2 btn btn-sm btn-danger">Deletar</button>
                                    </td>
                                </tr>             
                            );
                        })}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default PersonList;


export function formatPhone(phone: string) {
    if (phone.length === 10) {
        return phone.replace(/(\d{2})(\d{4})(\d{4})/, "($1) $2-$3");
    }
    return phone.replace(/(\d{2})(\d{5})(\d{4})/, "($1) $2-$3");
}