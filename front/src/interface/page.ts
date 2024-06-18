import { Person } from "./person";

export interface Page {
    page: {
        size: number;
        totalElements: number;
        totalPages: number;
        number: number;
    };
    content: Person[];


}