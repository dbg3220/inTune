// import 

import { Cart } from "./cart";

export interface User {
    id: number;
    username: string;
    cart: Cart;
}