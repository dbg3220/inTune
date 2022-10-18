import { Injectable } from '@angular/core';
import { Product } from './product';
import { PRODUCTS } from './mock-products';
import { Observable, of } from 'rxjs';
import { MessageService } from './message.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private messageService: MessageService, private http: HttpClient) { }

  private productsURL = 'api/products';  // URL to web api

  getProducts(): Observable<Product[]> {
    this.messageService.add('ProductService: fetched products')
    return this.http.get<Product[]>(this.productsURL)
      // .pipe(
      //   catchError(this.handleError<Product[]>('getProducts', []))
      // )
  }

  getProduct(id: number): Observable<Product> {
    const product = PRODUCTS.find(p => p.id === id)!;
    this.messageService.add(`ProductService: fetched hero id=${id}`)
    return of(product);
  }



}
