

export interface User {
    id: number;
    username: string;
    productsPurchased: number[];
    cart: {id:number,quantities:[],products:[]}
}
