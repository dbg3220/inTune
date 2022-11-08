import { Component, OnInit } from '@angular/core';

import { Product } from '../product';
// import { PRODUCTS } from '../mock-products';
import { ProductService } from '../product.service';
import { MessageService } from '../message.service';
import {filter, Subject, takeUntil} from "rxjs";
import { UserService } from '../user.service';
import {
  FormGroup,
  FormBuilder,
  Validators,
} from "@angular/forms";
import { ThisReceiver } from '@angular/compiler';
import { User } from '../user';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {

  products: Product[] = [];
  filteredProducts: Product[] = [];
  componentDestroyed$ = new Subject();
  selectedProduct?: Product;
  user: string = "";
  isAdmin: boolean = false;
  form!: FormGroup;
  test: string = "BRASS";

  onSelect(product: Product): void {
    this.selectedProduct = product;
    this.messageService.add(`ProductsComponent: Selected product id=${product.id}`);
  }
  constructor(private productService: ProductService,
    private messageService: MessageService,
    private userService: UserService,
    private fb: FormBuilder) { }
    searchText: any;

    getProducts(): void {
      this.productService.fetchProducts().subscribe(products =>
      {
        this.products = products;
        this.productService.setProductsView(this.products);
        this.filteredProducts = JSON.parse(JSON.stringify(this.products)); // clone
        this.productService.setProductsClone(this.products);
      });
      this.productService.getProductsAsObservable().pipe(filter(products => !!products), takeUntil(this.componentDestroyed$))
      .subscribe(products => this.products = products);
    }

  add(name: string, price: number, quantity: number, description: string, image: string): void {
    name = name.trim();
    if (!name) { return; }
    this.productService.addProduct({ name, price, quantity, description, image } as Product)
      .subscribe(product => {
        this.products.push(product);
      });
  }

  delete(product: Product): void {
    this.products = this.products.filter(p => p !== product);
    this.productService.deleteProduct(product.id).subscribe();
  }

  toNumber(price: string): number {
    return Number(price);
  }


  ngOnInit(): void {
    this.form = this.fb.group({
      name: ['', Validators.required],
      price: ['', Validators.required],
      category: ['', Validators.required],
      quantity: ['', Validators.required],
      description: ['', Validators.required],
      image: ['', Validators.required],
    });
    this.getProducts();
    this.userService.getCurrentUser().pipe(filter(user => !!user))
      .subscribe((user: User) =>{
        this.user = user;
      });
      if (this.user == "admin"){
        this.isAdmin = true;
      }
    }

filterByCategory(category: string){
  if(category === ""){
    this.filteredProducts = this.products;
  }
  else{
  for (let i = 0; i < this.products.length; ++i) {
    if(this.products[i].category === category){
      this.filteredProducts.push(this.products[i]);
    }
  }
}
  this.productService.setProductsView(this.filteredProducts);
}

reset() {
  this.searchText = '';
  this.productService.getClonedProductsAsObservable().subscribe(cloned => {
    // console.log('Cloned: ', cloned);
    this.productService.setProductsView(cloned);
  });
}

changeSearch($event: any, category: string) {
  this.filteredProducts = [];
  this.searchText = $event;
  this.productService.getClonedProductsAsObservable().subscribe(cloned => {
    // console.log('Cloned: ', cloned);
    this.productService.setProductsView(cloned);
  });
  // console.log('Searching ...', $event);
  this.filterByCategory(category);
  this.productService.setProductsView(this.filteredProducts.filter(item => item.name.toLowerCase().includes(this.searchText.toLowerCase())));
}

changeFilter(category: string){
  this.filteredProducts = [];
  this.productService.getClonedProductsAsObservable().subscribe(cloned => {
    // console.log('Cloned: ', cloned);
    this.productService.setProductsView(cloned);
  });
  // console.log('Searching ...', $event);
  this.filterByCategory(category);
  this.productService.setProductsView(this.filteredProducts.filter(item => item.name.toLowerCase().includes(this.searchText.toLowerCase())));
}

}
