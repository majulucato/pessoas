import { Contacts } from "./contacts";

export interface Person {
    id?: number,
    name: string,
    cpf: string,
    birthDate: string,
    contacts: Array<Contacts>,
}