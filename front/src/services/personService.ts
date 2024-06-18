import { Person } from '../interface/person';
import axios from 'axios';

export const personService = {

    getById: async (id: string) => {
        const response = await axios.get(`http://localhost:8081/pessoa/${id}`);
        return response.data;
    },

    getPersonList: async (page:number, size:number, param:string) => {
        const response = await axios.get(`http://localhost:8081/pessoa/all?page=1&size=2%20&page=1&size=2&param`);
        //const response = await axios.get(`http://localhost:8081/pessoa/all?page=${page}&size=${size}&param` + param ?'='+param:'');
        return response.data;
    },

    createPerson: async (person: Person) => {
        const response = await axios.post('http://localhost:8081/pessoa', person);
        console.log(response.data);
        return response.data;
    },

    updatePerson: async (person: Person) => {
        const response = await axios.put('http://localhost:8081/pessoa', person);
        return response.data;
    },

    deletePerson: async (id: number) => {
        const response = await axios.delete(`http://localhost:8081/pessoa/${id}`);
        return response.data;
    },

}

export default personService;