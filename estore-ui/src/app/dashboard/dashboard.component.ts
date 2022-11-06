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
  componentDestroyed$ = new Subject(); // tracks components lifecycle for subscription of the observable, component will automatically repaint if data changes
  topProducts: Product[] = [];
  test: boolean = false;
  filteredProducts: Product[] = [];

  //Merriweather Font
  
  constructor(private productService: ProductService) { }

    ngOnInit(): void {
      this.getProducts();
      this.cloneProducts();
    }

    getProducts(): void {
      this.productService.getProductsAsObservable().pipe(filter(products => !!products), takeUntil(this.componentDestroyed$))
        .subscribe(products => this.products = products.slice(1, 5));
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

    filterByCategory(category: string){
      this.filteredProducts = [];
      this.productService.getClonedProductsAsObservable().subscribe(cloned => {
        this.productService.setProductsView(cloned);
      });
      if(category === ""){
        return this.productService.setProductsView(this.products);
      }
      for (let i = 0; i < this.products.length; ++i) {
        if(this.products[i].category === category){
          this.filteredProducts.push(this.products[i]);
        }
      }
      this.productService.setProductsView(this.filteredProducts);
    }
}
