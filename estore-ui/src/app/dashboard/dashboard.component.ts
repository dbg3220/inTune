import {AfterViewInit, ChangeDetectorRef, Component, OnInit} from '@angular/core';
import { Product } from '../product';
import { ProductService } from '../product.service';
import {filter, Subject, takeUntil} from "rxjs";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: [ './dashboard.component.css' ]
})
export class DashboardComponent implements OnInit, AfterViewInit {
  products: Product[] = [];
  topProducts: Product[] = [];
  componentDestroyed$ = new Subject(); // tracks components lifecycle for subscription of the observable, component will automatically repaint if data changes

  constructor(private productService: ProductService, private cdRef : ChangeDetectorRef) { }

  ngOnInit(): void {
    this.getProducts();
    this.cloneProducts();
  }

  getProducts(): void {
    this.productService.getProductsAsObservable().pipe(filter(products => !!products), takeUntil(this.componentDestroyed$))
      .subscribe(products => this.products = products.slice(1, 5));
  }

  ngAfterViewInit(): void {
    // console.log('after view init');
    this.productService.getClonedProductsAsObservable().pipe(filter(products => !!products), takeUntil(this.componentDestroyed$))
      .subscribe(products => this.products = products.slice(1, 5));
      this.cdRef.detectChanges();
    }

    cloneProducts(){
      this.productService.fetchProducts().subscribe(topProducts =>
        {
          this.topProducts = topProducts.slice(0,3);
          this.productService.setProductsView(this.topProducts);
          this.productService.setProductsClone(this.topProducts);
        });
    }

    sortList(products : Product[]){
      if (products.length == 0 ){
        return [];
      }
      products.sort(function(a,b){return b.quantitySold - a.quantitySold});
      return products;
    }
}
