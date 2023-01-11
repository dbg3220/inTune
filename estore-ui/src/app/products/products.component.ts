import { Component, OnInit } from '@angular/core';
import { Product } from '../product';
import { ProductService } from '../product.service';
import { MessageService } from '../message.service';
import { filter, Subject, takeUntil} from "rxjs";
import { UserService } from '../user.service';
import {
  FormGroup,
  FormBuilder,
  Validators,
} from "@angular/forms";
import { User } from '../user';
import { Category } from '../category';
import { Review } from '../review';

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
  user: User | undefined;
  isAdmin: boolean = false;
  form!: FormGroup;
  alreadyExists: boolean = false;
  invalid: boolean = false;


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

  add(name: string, price: number, category: Category, quantity: number, description: string, image: string): void {
    //TODO this method had filter conditionals that were deleted, redo if necessary
    var id = 0;
    var reviews: Review[] = []
    //TODO redo this line
    this.productService.addProduct({ id, name, price, category, quantity, description, image, reviews } as Product)
      .subscribe(product => {
        this.products.push(product);
      });
      this.alreadyExists = false;
  }

  delete(product: Product): void {
    this.products = this.products.filter(p => p !== product);
    this.productService.deleteProduct(product.id).subscribe();
  }

  toNumber(price: string): number {
    return Number(price);
  }

  /**
   * Returns a category enum matching the parameter string,if
   * no match than value OTHER is returned
   * @param category The input value of a category
   */
  toCategory(categoryName: string): Category{
    var str = categoryName.toUpperCase();
    switch(str){
      case "STRING":
        return Category.STRING;
      case "WOODWIND":
        return Category.WOODWIND;
      case "BRASS":
        return Category.BRASS;
      case "KEYBOARD":
        return Category.KEYBOARD;
      case "PERCUSSION":
        return Category.PERCUSSION;
      default:
        return Category.OTHER;
    }
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
      if (this.user?.username == "admin"){
        this.isAdmin = true;
      }
      console.log(this.user);
      console.log(this.isAdmin);
    }

filterByCategory(category: Category){
  if(category == Category.OTHER){
    this.filteredProducts = this.products;
  }
  else{
  for (let i = 0; i < this.products.length; ++i) {
    if(this.products[i].category == category){
      this.filteredProducts.push(this.products[i]);
    }
  }
}
  this.productService.setProductsView(this.filteredProducts);
}

reset() {
  this.searchText = '';
  this.productService.getClonedProductsAsObservable().subscribe(cloned => {
    this.productService.setProductsView(cloned);
  });
}

changeSearch($event: any, category: Category) {
  this.filteredProducts = [];
  this.searchText = $event;
  this.productService.getClonedProductsAsObservable().subscribe(cloned => {
    this.productService.setProductsView(cloned);
  });
  this.filterByCategory(category);
  this.productService.setProductsView(this.filteredProducts.filter(item => item.name.toLowerCase().includes(this.searchText.toLowerCase())));
}

changeFilter(category: Category){
  this.filteredProducts = [];
  this.productService.getClonedProductsAsObservable().subscribe(cloned => {
    this.productService.setProductsView(cloned);
  });
  this.filterByCategory(category);
  this.productService.setProductsView(this.filteredProducts.filter(item => item.name.toLowerCase().includes(this.searchText.toLowerCase())));
}
}
