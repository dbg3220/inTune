import { Injectable } from '@angular/core';
import { User } from './user';
// import { PRODUCTS } from './mock-products';
import { Observable, of } from 'rxjs';
import { MessageService } from './message.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HeroService {

  constructor(private messageService: MessageService, private http: HttpClient) { }

  private usersURL = 'api/users';  // URL to web api
  private users: any = new BehaviorSubject([]);
  private searchFilterProductsClone: any = new BehaviorSubject([]);

  // getProducts(): Observable<Product[]> {
  //   this.messageService.add('ProductService: fetched products')
  //   return this.http.get<Product[]>(this.productsURL)
  //     // .pipe(
  //     //   catchError(this.handleError<Product[]>('getProducts', []))
  //     // )
  // }

  

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

}
