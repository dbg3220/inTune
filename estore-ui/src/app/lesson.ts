import { Category } from "./category";
import { WeekDay } from "@angular/common";

export interface Lesson{
    id: number;
    category: Category;
    instructor: string;
    weekday: WeekDay;
    startTime: number;
    userID: number;
    price: number;
    name: string;
}
