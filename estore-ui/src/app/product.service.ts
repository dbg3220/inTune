import {Injectable} from '@angular/core';
import {Product} from './product';
// import { PRODUCTS } from './mock-products';
import {Observable, of} from 'rxjs';
import {MessageService} from './message.service';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {catchError, map, tap} from 'rxjs/operators';
import {BehaviorSubject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private messageService: MessageService, private http: HttpClient) {
  }

  private productsURL = 'http://localhost:8080/products';  // URL to web api
  private products: BehaviorSubject<any> = new BehaviorSubject(null);
  private searchFilterProductsClone: BehaviorSubject<any> = new BehaviorSubject(null);
  private _productCart = new BehaviorSubject<Product[]>([]);
  readonly productCart$ = this._productCart.asObservable();
  private productCart: Product[] = [];
  private sessionCart: BehaviorSubject<any> = new BehaviorSubject(null);

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

  getProductsAsObservable() {
    return this.products.asObservable();
  }

  getClonedProductsAsObservable() {
    return this.searchFilterProductsClone.asObservable();
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
    this.products.next(this.searchFilterProductsClone);
  }

  updateProduct(product: Product): Observable<any> {
    return this.http.put(this.productsURL, product, this.httpOptions)
  }

  addProduct(product: Product): Observable<Product> {
    return this.http.post<Product>(this.productsURL, product, this.httpOptions);
  }

  deleteProduct(id: number): Observable<Product> {
    const url = `${this.productsURL}/${id}`;
    return this.http.delete<Product>(url, this.httpOptions)
  }

  addToCart(product: Product) {
    let found: boolean = false;
    let storageProducts = JSON.parse(sessionStorage['cartObject']) || this.productCart || [];
    for (let x of storageProducts) {
      if (product.id === x.id) {
        found = true;
        product.quantity++;
      }
    }
    //if(!found) {
    if (!found) {
      storageProducts.push(product);
      product.quantity = 1;
    }
    this.productCart = storageProducts;
    this._productCart.next(Object.assign([], this.productCart));
    //}
    this.updateSessionCart(storageProducts);
  }

  removeToCart(product: Product) {
    let found: boolean = false;
    this.productCart.forEach((item, index) => {
      if (item.id === product.id) {
        if (product.quantity > 1) {
          product.quantity--;
        } else {
          this.productCart.splice(index, 1);
        }
      }
    });

    this._productCart.next(Object.assign([], this.productCart));
    //this.updateSessionCart();

    if (this.productCart.length == 0) {
      sessionStorage.clear();
    }
  }

  getSessionStorageCart(): Observable<Product[]> {
    return this.sessionCart.asObservable();
  }

  getCart(): Observable<Product[]> {
    return this.productCart$;
  }

  //return JSON.parse(JSON.stringify(sessionStorage.getItem("cartObject"))).asObservable();

  updateSessionCart(products:any) {
    sessionStorage.setItem("cartObject", JSON.stringify(products));
    this.sessionCart.next(Object.assign([], products));

  }

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

}
