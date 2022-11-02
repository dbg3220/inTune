import {Component, OnInit} from '@angular/core';
import { Product } from './product';
import { ProductService } from './product.service';
import {BehaviorSubject, filter, Subject, takeUntil} from "rxjs";
import { User } from './user';

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
  private userSource = new BehaviorSubject('test');
  currentUser = this.userSource.asObservable();

  constructor(private productService: ProductService) {}

  ngOnInit() {
    this.getProducts();
  }

  changeUser(user: string) {
    this.userSource.next(user);
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

  changeSearch($event: any) {
    this.searchText = $event;
    // console.log('Searching ...', $event);
    this.productService.setProductsView(this.filteredItems.filter(item => item.name.toLowerCase().includes(this.searchText.toLowerCase())));
  }

  reset() {
    this.searchText = '';
    this.productService.getClonedProductsAsObservable().subscribe(cloned => {
      // console.log('Cloned: ', cloned);
      this.productService.setProductsView(cloned);
    });
  }
}
