import { Component, OnInit, AfterViewChecked, Output } from '@angular/core';
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
export class LoginComponent implements OnInit {
  login!: FormGroup;
  users: User[] = [];
  componentDestroyed$ = new Subject(); // tracks components lifecycle for subscription of the observable, component will automatically repaint if data changes
  exists: boolean = false;
  message: string = "";
  user: User | undefined;
  created: boolean = false;



  async onLogin() {
    const username = this.login.get('username')?.value;
    for (let user of this.users) {
      if (user.username === username) {
        this.created = false
        this.exists = true;
        console.log("exists");
        this.user = this.users.find(user => user.username === username);
        this.userService.setCurrentUser(this.user);
        console.log(this.user);
        sessionStorage.setItem('user',JSON.stringify(this.user));
        console.log("login");
        this.productService.fetchProducts().subscribe(products => {
          let temp:Product[] = products.filter((product: any) => {
            for (let x in user.cart.products) {
              let y = user.cart.products[x];
              if (product.id === y) {
                let copy = JSON.parse(JSON.stringify(product));
                copy.quantity = user.cart.quantities[x];
                console.log("copy", copy);
               return(copy);
              }
            }
          });
          this.productService.quickAddToCart(temp);
          console.log("user cart from backend", temp)
        })
        return
      }
      this.userService.getCurrentUser().pipe(filter(user => !!user))
        .subscribe(user =>{
          this.user = user;
          console.log("useritem?",user);
        });

    }
    console.log("does not exist");
    this.exists = false;
    this.userService.addUser({ username } as User)
      .subscribe(user => {
        this.users.push(user);
      });
    console.log(this.login.value + "added");
    this.user = this.users.find(user => user.username === username);
    this.created = true;
    this.message = "It seems you weren't registered. We have added you as a user. To confirm, please log in again.";
    console.log("sign up")
    console.log(this.user)
  }

  async onLogout() {
    if (this.user?.username != "admin")
      await this.productService.saveUser().subscribe((response:any) => {
      console.log('got response',response)
      sessionStorage.clear();
      // sessionStorage.setItem('user',JSON.stringify(response));
      this.userService.setCurrentUser(response);
      this.user = response;
      this.exists = false;
      window.location.reload();
    });
    else {
      sessionStorage.clear();
      this.userService.setCurrentUser(undefined);
      this.user = undefined;
      this.exists = false;
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
    private productService: ProductService
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
    }``
  }


}

