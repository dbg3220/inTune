import { Product } from "./product";

export interface Cart {
    id: number;
    username: string;
    items: Product[];
    
}