import { Review } from "./review";
import { Category } from "./category";

export interface Product {
    id: number;
    name: string;
    price: number;
    category: Category;
    quantity : number;
    description: string;
    image: string;
    reviews: Review[];
}