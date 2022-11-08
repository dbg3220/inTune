import { Product } from "./product";

export interface User {
    id: number;
    username: string;
    items: Product[];
    
}