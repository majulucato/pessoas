import { Contacts } from "../interface/contacts";
import { formatPhone } from "../utils/PhoneUtils";


const ContactList = (contacts: Contacts[]) => {
    return (
        <table className="table-auto w-full">
            <thead>
                <tr>
                    <th className="px-4 py-2">Nome</th>
                    <th className="px-4 py-2">Telefone</th>
                    <th className="px-4 py-2">Email</th>
                </tr>
            </thead>
            <tbody>
                {contacts.map((contact, index) => (
                    <tr key={index} className="border-b hover:bg-orange-100">
                        <td className="p-3 px-5">{contact.name}</td>
                        <td className="p-3 px-5">{formatPhone(contact.phone.toString())}</td>
                        <td className="p-3 px-5">{contact.email}</td>
                    </tr>
                ))}
            </tbody>
        </table>

    );
};

export default ContactList;