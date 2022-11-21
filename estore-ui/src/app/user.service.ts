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

  constructor(private messageService: MessageService, private http: HttpClient) {}

  private usersURL = 'http://localhost:8080/users';  // URL to web api
  private users: BehaviorSubject<any> = new BehaviorSubject(null);
  private _user: BehaviorSubject<any> = new BehaviorSubject(null);
  private user: User | undefined;
  readonly currentUser$ = this._user.asObservable();

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


  getUsersAsObservable() {
    return this.users.asObservable();
  }

  getUser(username: string): Observable<User> {
    const url = `${this.usersURL}/${username}`;
    return this.http.get<User>(url)
  }

  getByUsername(username: string): Observable<User> {
    const user = this.getUser(username);
    return user;
  }

  addUser(user: User): Observable<User> {
    return this.http.post<User>(this.usersURL, user);
  }

  getCurrentUser(): Observable<User>
  {
    return this.currentUser$;
  }

  setCurrentUser(user: User | undefined) {
    this.user = user
    this._user.next(this.user);
    console.log("User set to: " + this.user?.username);
  }

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    
  };

}