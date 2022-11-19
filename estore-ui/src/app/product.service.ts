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
  public _productCart = new BehaviorSubject<Product[]>(JSON.parse(sessionStorage.getItem('cart') || "[]") || []);
  readonly productCart$ = this._productCart.asObservable();
  public productCart: Product[] = JSON.parse(sessionStorage.getItem('cart') || "[]") || [];
  public checkout: boolean = false;
  // getProducts(): Observable<Product[]> {
  //   this.messageService.add('ProductService: fetched products')
  //   return this.http.get<Product[]>(this.productsURL)
  //     // .pipe(
  //     //   catchError(this.handleError<Product[]>('getProducts', []))
  //     // )
  // }

  defaultCart()
  {
    let cart = JSON.parse(sessionStorage.getItem('cart') || "[]") || [];
    console.log("setting default cart", cart);
    if(cart.length > 0 )
    {
      if(cart.products && cart.quantities)
      {
        let temp:any = [];
        this.fetchProducts().subscribe(products => {
          temp = products.filter(product => {
            let counter = 0;
            for(let x of cart.products)
            {
              if(product.id  === x)
              {
                let copy = JSON.parse(JSON.stringify(product));
                copy.quantity = cart.quantities[counter];
                counter++;

                return copy;
              }
            }
          })
        })
        cart = temp;
      }
    }
    this.productCart = cart;
    this._productCart.next(cart);
    return cart;
  }
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
    product.reviews = [];
    product.quantitySold = 0;
    return this.http.post<Product>(this.productsURL, product, this.httpOptions);
  }

  deleteProduct(id: number): Observable<Product> {
    const url = `${this.productsURL}/${id}`;
    return this.http.delete<Product>(url, this.httpOptions)
  }

  addToCart(product: Product) {
    let found: boolean = false;
    let itemClone = JSON.parse(JSON.stringify(product));
    for (let x of this.productCart) {
      if (itemClone.id === x.id) {
        found = true;
        if(x.quantity < itemClone.quantity) {
          x.quantity++;
        }
        else
        {
          window.alert("Item limit exceeded")
        }
      }
    }
    if (!found) {
      this.productCart.push(itemClone);
      itemClone.quantity = 1;
    }
    this._productCart.next(Object.assign([], this.productCart));
    sessionStorage.setItem('cart', JSON.stringify(this.productCart));
  }

  quickAddToCart(cartItems: Product[])
  {
    this.productCart = cartItems;
    this._productCart.next(Object.assign([], this.productCart));
    sessionStorage.setItem('cart', JSON.stringify(this.productCart));
  }

  removeToCart(product: Product) {
    let found: boolean = false;
    let itemClone = JSON.parse(JSON.stringify(product));
    this.productCart.forEach((item, index) => {
      if (item.id === itemClone.id) {
        if (itemClone.quantity > 1) {
          item.quantity--;
        } else {
          this.productCart.splice(index, 1);
        }
      }
    });


    this._productCart.next(Object.assign([], this.productCart));
    sessionStorage.setItem('cart', JSON.stringify(this.productCart));
  }

  getCart(): Observable<Product[]> {
    return this.productCart$;
  }

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  saveUser() {
    console.log('saving user cart with item ',this.productCart)
    let user = JSON.parse(sessionStorage.getItem('user') || '{}')
    // let cart = JSON.parse(sessionStorage.getItem('cart') || '[]')
    // user.cart = cart;
    let productList = [];
    let quantityList = [];
    for(let x of this.productCart)
    {
      productList.push(x.id);
      quantityList.push(x.quantity);
    }

    let data = {
      id: user.id,
      cart: {
        products: productList,
        quantities: quantityList,
        id: user.cart.id
      },
      productsPurchased: user.productsPurchased || [],
      username: user.username
    }
    console.log("sending query to back end",data);
    // sessionStorage.clear();
    // return new Observable();
    return this.http.put('http://localhost:8080/users',data,this.httpOptions)
  }

  saveStock(product: Product)
  {

    return this.http.put('http://localhost:8080/products',product,this.httpOptions)
  }

  saveUserCheckout() {
    let user = JSON.parse(sessionStorage.getItem('user') || '{}')
    let data = {
      id: user.id,
      cart: {
        products: [],
        quantities: [],
        id: user.cart.id
      },
      productsPurchased: user.productsPurchased || [],
      username: user.username
    }
    console.log("checkout purchase item",data);
    // sessionStorage.clear();
    // return new Observable();
    return this.http.put('http://localhost:8080/users',data,this.httpOptions)

  }
}

