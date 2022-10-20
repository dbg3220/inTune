import { Component, OnInit } from '@angular/core';
import { Product } from '../product';
import { ProductService } from '../product.service';
import {filter, Subject, takeUntil} from "rxjs";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: [ './dashboard.component.css' ]
})
export class DashboardComponent implements OnInit {
  products: Product[] = [];
  componentDestroyed$ = new Subject();

  constructor(private productService: ProductService) { }

    ngOnInit(): void {
      this.getProducts();
    }

    getProducts(): void {
      this.productService.getProductsAsObservable().pipe(filter(products => !!products), takeUntil(this.componentDestroyed$))
        .subscribe(products => this.products = products.slice(0, 5));
    }
}
