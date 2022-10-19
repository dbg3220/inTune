import { Injectable } from '@angular/core';
import { Product } from './product';
import { PRODUCTS } from './mock-products';
import { Observable, of } from 'rxjs';
import { MessageService } from './message.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  constructor(private messageService: MessageService, private http: HttpClient) { }

  private productsURL = 'api/products';  // URL to web api
  private products: any = new BehaviorSubject([]);
  private searchFilterProductsClone: any = new BehaviorSubject([]);

  fetchProducts() {
    this.messageService.add('ProductService: fetched products')
    return this.http.get<Product[]>(this.productsURL);
  }

  getProducts(): Observable<Product[]> {
    return this.products.asObservable();
  }

  getFilteredProducts(): Observable<Product[]> {
    return this.searchFilterProductsClone;
  }

  getProduct(id: number): Observable<Product> {
    const product = PRODUCTS.find(p => p.id === id)!;
    this.messageService.add(`ProductService: fetched hero id=${id}`)
    return of(product);
  }

  setProductsView(prods: Product[]) {
    this.products.next(prods);
  }

  setProductsClone(prods: Product[]) {
    this.searchFilterProductsClone.next(prods);
  }

  resetFilters() {
    this.products = this.searchFilterProductsClone;
  }
}
