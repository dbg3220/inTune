import { Component, OnInit } from '@angular/core';

import { Product } from '../product';
import { PRODUCTS } from '../mock-products';
import { ProductService } from '../product.service';
import { MessageService } from '../message.service';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {

  products: Product[] = [];


  selectedProduct?: Product;

  onSelect(product: Product): void {
    this.selectedProduct = product;
    this.messageService.add(`ProductsComponent: Selected product id=${product.id}`);
  }

  constructor(private productService: ProductService,
    private messageService: MessageService) { }

  getProducts(): void {
    this.productService.getProducts().subscribe(products => this.products = products);
  }

  add(name: string): void {
    name = name.trim();
    if (!name) { return; }
    this.productService.addProduct({ name } as Product)
      .subscribe(product => {
        this.products.push(product);
      });
  }

  ngOnInit(): void {
    this.productService.getProducts().subscribe((products => {
      this.products = products;
    } ));
    // setTimeout(() => {
    //     this.products = [];
    //     console.log("Delayed for 1 second.");
    //   }, 3000
    // )
  }

}
