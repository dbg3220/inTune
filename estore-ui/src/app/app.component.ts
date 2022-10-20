import {Component, OnInit} from '@angular/core';
import { Product } from './product';
import { ProductService } from './product.service';

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

  constructor(private productService: ProductService) {
  }

  ngOnInit() {
    this.getProducts();
  }

  getProducts(): void {
    this.productService.fetchProducts().subscribe(products =>
    {
      this.products = products.slice(1, 5);
      this.productService.setProductsView(this.products);
      this.filteredItems = JSON.parse(JSON.stringify(this.products)); // clone
      this.productService.setProductsClone(this.products);
    });
  }

  changeSearch($event: any) {
    this.searchText = $event;
    console.log('Searching ...', $event);
    this.productService.setProductsView(this.filteredItems.filter(item => item.category.includes(this.searchText)));
  }

  reset() {
    this.searchText = '';
    this.productService.resetFilters();
    this.getProducts();
  }
}
