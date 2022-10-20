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
export class UserService {

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

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.usersURL)
  }

  getUser(username: string): Observable<User> {
    const url = `${this.usersURL}/${username}`;
    return this.http.get<User>(url)
  }

  getByUsername(username: string): Observable<User> {
    const user = this.getUser(username);
    return user;
  }



  

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

}
