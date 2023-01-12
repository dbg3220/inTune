import { Component, OnInit } from '@angular/core';
import { Product } from './product';
import { ProductService } from './product.service';
import { BehaviorSubject, filter, Subject, takeUntil } from "rxjs";
import { User } from './user';
import { UserService } from './user.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'inTune';
  products: Product[] = [];
  filteredItems: Product[] = [];
  searchText: any;
  componentDestroyed$ = new Subject();
  user: User | undefined;
  isAdmin = false;

  constructor(private productService: ProductService, private userService: UserService) {}

  ngOnInit() {
    this.getProducts();
    this.userService.getCurrentUser().pipe(filter(user => !!user))
      .subscribe((user: User) =>{
        this.user = user;
      });
    if (this.user!?.username == "admin"){
      this.isAdmin = true;
    }
  }

  ngOnChanges() {
    this.userService.getCurrentUser().pipe(filter(user => !!user))
      .subscribe((user: User) =>{
        this.user = user;
      });
    if (this.user!.username == "admin"){
      this.isAdmin = true;
    }
    console.log("user changes ... ", this.user)
  }

  getProducts(): void {
    this.productService.fetchProducts().subscribe(products =>
    {
      this.products = products;
      this.productService.setProductsView(this.products);
      this.filteredItems = JSON.parse(JSON.stringify(this.products)); // clone
      this.productService.setProductsClone(this.products);
    });

    this.productService.getProductsAsObservable().pipe(filter(products => !!products), takeUntil(this.componentDestroyed$))
      .subscribe(products => this.products = products);
  }
}
