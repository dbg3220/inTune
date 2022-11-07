import { Product } from "./product";

export interface Cart {
    id: number;
    items: Product[];
    quantities: number[];
    
}