import { Injectable } from '@angular/core';
import { InMemoryDbService } from 'angular-in-memory-web-api';
import { Product } from './product';

@Injectable({
  providedIn: 'root',
})
export class InMemoryDataService implements InMemoryDbService {
  createDb() {
    const products = [
      {
        id: 1,
        name: "Violin",
        price: 1000,
        category: "string",
        subcategory: "violin",
        quantity: 1
      },
      {
        id: 2,
        name: "Guitar",
        price: 1000,
        category: "string",
        subcategory: "guitar",
        quantity: 1
      },
      {
        id: 3,
        name: "Bass",
        price: 1000,
        category: "string",
        subcategory: "bass",
        quantity: 1
      },
      {
        id: 4,
        name: "test",
        price: 1000,
        category: "test",
        subcategory: "test",
        quantity: 1
      },
      {
        id: 5,
        name: "Hi",
        price: 1000,
        category: "hi",
        subcategory: "hi",
        quantity: 1
      },
    ];
    return {products};
  }

  // Overrides the genId method to ensure that a hero always has an id.
  // If the heroes array is empty,
  // the method below returns the initial number (11).
  // if the heroes array is not empty, the method below returns the highest
  // hero id + 1.
  genId(products: Product[]): number {
    return products.length > 0 ? Math.max(...products.map(product => product.id)) + 1 : 11;
  }
}