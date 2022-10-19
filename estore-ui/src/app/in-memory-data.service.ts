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
        quantity: 1,
        image: "https://cdn.shopify.com/s/files/1/0182/0563/products/MaestroVNTop_682x1800_7159c927-5752-43e0-9c2e-a19df87bf18b_1200x.JPG?v=1621442263"
      },
      {
        id: 2,
        name: "Guitar",
        price: 1000,
        category: "string",
        subcategory: "guitar",
        quantity: 1,
        image : "https://upload.wikimedia.org/wikipedia/commons/4/45/GuitareClassique5.png"
      },
      {
        id: 3,
        name: "Bass",
        price: 1000,
        category: "string",
        subcategory: "bass",
        quantity: 1,
        image : "https://usa.yamaha.com/files/Image-Index_TRBX_1080x1080_2e72e1d0e9ac01702086b34f5f94a50c.jpg?impolicy=resize&imwid=396&imhei=396"
      },
      {
        id: 4,
        name: "test",
        price: 1000,
        category: "test",
        subcategory: "test",
        quantity: 1,
        image : "https://images.ctfassets.net/lh3zuq09vnm2/1ENjVxhiWtm7LCwLFhgiW9/3132b27acddbfab3131686ea80310fdc/conducting-usability-test_yon4BQT.svg"
      },
      {
        id: 5,
        name: "Hi",
        price: 1000,
        category: "hi",
        subcategory: "hi",
        quantity: 1,
        image: null
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