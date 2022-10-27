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
        price: 99.99,
        category: "string",
        subcategory: "violin",
        quantity: 0,
        image: "https://cdn.shopify.com/s/files/1/0182/0563/products/MaestroVNTop_682x1800_7159c927-5752-43e0-9c2e-a19df87bf18b_1200x.JPG?v=1621442263"
      },
      {
        id: 2,
        name: "Guitar",
        price: 159.99,
        category: "string",
        subcategory: "guitar",
        quantity: 0,
        image : "https://upload.wikimedia.org/wikipedia/commons/4/45/GuitareClassique5.png"
      },
      {
        id: 3,
        name: "Bass",
        price: 219.99,
        category: "string",
        subcategory: "bass",
        quantity: 0,
        image : "https://images.musicstore.de/images/1280/j-und-d-bass-guitar-jb-black_1_BAS0000913-001.jpg"
      },
      {
        id: 4,
        name: "Drum Set",
        price: 529.99,
        category: "percussion",
        subcategory: "drums",
        quantity: 0,
        image : "https://armadillo.sirv.com/assets/gallery/d2522rsp/d2522rsp.jpg"
      },
      {
        id: 5,
        name: "Cello",
        price: 249.99,
        category: "string",
        subcategory: "cello",
        quantity: 0,
        image: "https://cdn.shopify.com/s/files/1/0182/0563/products/MaestroCelloTop_400x1040_84cb4427-ca2b-4c9f-b40a-5621ad795ebb_800x.JPG?v=1569187496"
      },
      {
        id: 6,
        name: "Clarinet",
        price: 49.99,
        category: "woodwind",
        subcategory: "clarinet",
        quantity: 0,
        image: "https://m.media-amazon.com/images/I/514wgFQDU+L.jpg"
      },
      {
        id: 7,
        name: "Bassoon",
        price: 199.99,
        category: "woodwing",
        subcategory: "bassoon",
        quantity: 0,
        image: "https://media.wwbw.com/is/image/MMGS7/M100E-Professional-Bassoon/L58593000000000-00-1600x1600.jpg"
      },
      {
        id: 8,
        name: "Trumpet",
        price: 199.99,
        category: "horn",
        subcategory: "trumpet",
        quantity: 0,
        image: "https://upload.wikimedia.org/wikipedia/commons/thumb/1/12/Yamaha_Trumpet_YTR-8335LA_crop.jpg/1200px-Yamaha_Trumpet_YTR-8335LA_crop.jpg"
      },
      {
        id: 9,
        name: "Triangle",
        price: 59.99,
        category: "percussion",
        subcategory: "triangle",
        quantity: 0,
        image: "https://m.media-amazon.com/images/I/71QNie7RV7L.jpg"
      },
    ];

    const users = [
      {
        id: 1,
        username: "admin",
        isAdmin: true,
      },
      {
        id: 2,
        username: "Hayden",
        isAdmin: true,
      },
      {
        id: 3,
        username: "Jonothan",
        isAdmin: true,
      },
      {
        id: 4,
        username: "Damon",
        isAdmin: true,
      },
      {
        id: 5,
        username: "user",
        isAdmin: false,
      },
    ];

    const carts = [
      {
        id: 1,
        username: "user",
        items: [],

      },
    ];


    return {products, users, carts};
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
