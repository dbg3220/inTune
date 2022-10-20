import { Injectable } from '@angular/core';
import { Product } from './product';
// import { PRODUCTS } from './mock-products';
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

  // getProducts(): Observable<Product[]> {
  //   this.messageService.add('ProductService: fetched products')
  //   return this.http.get<Product[]>(this.productsURL)
  //     // .pipe(
  //     //   catchError(this.handleError<Product[]>('getProducts', []))
  //     // )
  // }

  fetchProducts() {
    this.messageService.add('ProductService: fetched products')
    return this.http.get<Product[]>(this.productsURL);
  }

  getProducts(): Observable<Product[]> {
    this.messageService.add('ProductService: fetched products')
    return this.http.get<Product[]>(this.productsURL)
  }
  getFilteredProducts(): Observable<Product[]> {
    return this.searchFilterProductsClone;
  }

  getProduct(id: number): Observable<Product> {
    const url = `${this.productsURL}/${id}`;
    return this.http.get<Product>(url)
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

  updateProduct(product: Product): Observable<any> {
    return this.http.put(this.productsURL, product, this.httpOptions)
  }

  addProduct(product: Product): Observable<Product> {
  return this.http.post<Product>(this.productsURL, product, this.httpOptions);
}
  deleteProduct(id : number): Observable<Product> {
    const url = `${this.productsURL}/${id}`;
    return this.http.delete<Product>(url, this.httpOptions)
  }

  

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

}
