import { Person } from "../interface/person";
import { formatPhone } from "../utils/PhoneUtils";

const PersonEdit = (person: Person) => {
    return (
        <div className="p-5 overflow-auto max-h-lvh">
            <table className="table-auto w-full">
                <thead>
                    <tr>
                        <th className="px-4 py-2">Nome</th>
                        <th className="px-4 py-2">Telefone</th>
                        <th className="px-4 py-2">Email</th>
                    </tr>
                </thead>
                <tbody>
                    {person.contacts.map((contact, index) => (
                        <tr key={index} className="border-b hover:bg-orange-100">
                            <td className="p-3 px-5">{contact.name}</td>
                            <td className="p-3 px-5">{formatPhone(contact.phone.toString())}</td>
                            <td className="p-3 px-5">{contact.email}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default PersonEdit;