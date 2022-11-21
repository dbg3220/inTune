import {Component, OnInit, AfterViewChecked, Output, AfterViewInit} from '@angular/core';
import { User } from '../user';
import { UserService } from '../user.service';
import { UsernameValidator } from '../custom-username.validator';
import {
  FormGroup,
  FormBuilder,
  Validators,
} from "@angular/forms";
import { filter, Subject, takeUntil } from 'rxjs';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import {ProductService} from "../product.service";
import {Product} from "../product";
// import { CustomUserValidator } from "../shared/custom-email.validator";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, AfterViewInit {
  login!: FormGroup;
  users: User[] = [];
  componentDestroyed$ = new Subject(); // tracks components lifecycle for subscription of the observable, component will automatically repaint if data changes
  exists: boolean = false;
  message: string = "";
  user: User | undefined;
  created: boolean = false;

  onLogin() {
    // this.productService.cleanup();
    const username = this.login.get('username')?.value;
    // user exists, transform cart items from backend to UI, remove any from cart that are no longer in stock
    // update user in service & sessionStorage
    for (let user of this.users) {
      if (user.username === username) {
        this.created = false
        this.exists = true;
        // console.log("exists");
        this.user = this.users.find(user => user.username === username);
        let loggedInUser = JSON.parse(JSON.stringify(user));
        console.log(this.user?.cart);

        this.userService.setCurrentUser(this.user);
        sessionStorage.setItem('user',JSON.stringify(this.user));

        let transformBackEndToCard: any[] = [];
        this.productService.fetchProducts().subscribe(products => {
          // console.log(typeof (products))
          let productClone = Object.assign([], products);
          // console.log('cloned: ', productClone, "\t", typeof(productClone))
          for (let key in user.cart.products) {
            // console.log(user.cart.products, user.cart.products[key], user.cart.quantities[key])
            for (let product of productClone) {
              // console.log(productClone[product])
              if (product['id'] == user.cart.products[key]){
                // console.log('found product from cart', product['id'], product['quantity'])
                if (product['quantity'] > 0) {
                  let newObj = {
                    category: product['category'],
                    description: product['description'],
                    id: product['id'],
                    image: product['image'],
                    name: product['name'],
                    price: product['price'],
                    quantity: user.cart.quantities[key],
                    reviews: product['reviews'],
                  }
                  transformBackEndToCard.push(newObj)
                  // console.log('cart addition =', newObj);
                } else {
                    const index = user.cart.products.indexOf(user.cart.products[key], 0);
                    if (index > -1) {
                      user.cart.products.splice(index, 1);
                    }
                  const index2 = user.cart.quantities.indexOf(user.cart.quantities[key], 0);
                  if (index2 > -1) {
                    user.cart.quantities.splice(index, 1);
                  }
                  window.alert('Your cart item ' + product['name'] + ' was removed from cart as item is not in stock anymore.' +
                    user.cart.quantities + user.cart.products)
                }
              }
            }
          }
          // update to latest after transformation from backend based on stock values
          loggedInUser.cart.products = user.cart.products
          loggedInUser.cart.quantities = user.cart.quantities
          this.user = loggedInUser
          this.userService.setCurrentUser(this.user);
          sessionStorage.setItem('user',JSON.stringify(this.user));
          this.productService.quickAddToCart(transformBackEndToCard);
          // console.log("user cart generated", temp)
        })
        this.router.navigate([`/dashboard`]);
        return
      }

      this.userService.getCurrentUser().pipe(filter(user => !!user))
        .subscribe(user =>{
          this.user = user;
          // console.log("useritem?",user);
        });

    }
    // user doesn't exist, create one and update service
    if (!this.exists) {
      // console.log("does not exist");
      this.exists = false;
      this.userService.addUser({ username } as User)
        .subscribe(user => {
          this.users.push(user);
        });
      // console.log(this.login.value + "added");
      this.user = this.users.find(user => user.username === username);
      this.created = true;
      this.message = "It seems you weren't registered. We have added you as a user. To confirm, please log in again.";
      // console.log("sign up")
      // console.log(this.user)
    }
  }

  async onLogout() {
    if (this.user?.username != "admin" && !this.productService.checkout) {
      await this.productService.saveUser().subscribe((response: any) => {
        // console.log('got response', response)
        sessionStorage.clear();
        // sessionStorage.setItem('user',JSON.stringify(response));
        this.userService.setCurrentUser(response);
        this.user = response;
        this.exists = false;
        this.productService.cleanup();
        window.location.reload();
      });
    } else {
      this.productService.checkout = false;
      sessionStorage.clear();
      this.userService.setCurrentUser(undefined);
      this.user = undefined;
      this.exists = false;
      this.productService.cleanup();
      window.location.reload();
    }
  }
  // when usr logs in
  // hit api end point to get user
  // route /users/?username= <--- query string put in url
  // get user object get from cart to reset cart in angular



  // onSignup() {
  //   const username = this.login.get('username')?.value;
  //   console.log(this.login.value);
  //   for (let user of this.users) {
  //     if (user.username === username) {
  //       this.exists = true;
  //       console.log("exists");
  //     }
  //     else {
  //   this.userService.addUser({ username } as User)
  //     .subscribe(user => {
  //       this.users.push(user);
  //     });
  //   console.log(this.login.value + "added");
  //   this.exists = true;
  //     }
  // }
  // }

  get username(){
    return this.login.get('username');
  }

  constructor(
    private userService: UserService,
    private fb: FormBuilder,
    private productService: ProductService,
    private router: Router
    // private usernameValidator: UsernameValidator
  ) {

  }

  ngOnInit(): void {
    this.userService.getUsers().pipe(filter(users => !!users)).subscribe((users: User[]) => this.users = users);
    this.login = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(20)]]
    });
    this.userService.getCurrentUser().pipe(filter(user => !!user))
      .subscribe(user =>{
        this.user = user;
      });
    if (this.user) {
      this.exists = true;
    }
  }

  ngAfterViewInit(): void {
    // console.log("checking user session ?", this.user);
    if (!this.user){
      sessionStorage.clear();
    }
  }


}

