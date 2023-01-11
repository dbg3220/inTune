import { Category } from "./category";
import { Weekday } from "./weekday";

export interface Lesson{
    id: number;
    category: Category;
    instructor: string;
    weekday: Weekday;
    startTime: number;
    userID: number;
    price: number;
    name: string;
}
