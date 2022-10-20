import {Component, OnInit} from '@angular/core';
import { Product } from './product';
import { ProductService } from './product.service';
import {filter, Subject, takeUntil} from "rxjs";

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

  constructor(private productService: ProductService) {}

  ngOnInit() {
    this.getProducts();
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
    this.productService.setProductsView(this.filteredItems.filter(item => item.category.includes(this.searchText)));
  }

  reset() {
    this.searchText = '';
    this.productService.getClonedProductsAsObservable().subscribe(cloned => {
      // console.log('Cloned: ', cloned);
      this.productService.setProductsView(cloned);
    });
  }
}
