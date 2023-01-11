import { Component, OnInit } from '@angular/core';
import {CartComponent} from "../cart/cart.component";
import {ProductService} from "../product.service";
import {Location} from "@angular/common";
import {Product} from "../product";
import {filter, Subject, takeUntil} from "rxjs";
import {Router} from "@angular/router";
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {User} from "../user";
import {UserService} from "../user.service";

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit {
  checkoutItems: Product[] = [];
  componentDestroyed$ = new Subject();
  subTotals: number = 0;
  subQuantity: number = 0;
  myForm!: FormGroup;
  product: Product[] = []
  user!: User

  constructor(
    private productService: ProductService,
    private userService: UserService,
    private location: Location,
    private router: Router,
    private fb: FormBuilder

  ) { }

  subTotal() {
    for (let product of this.checkoutItems) {
      this.subTotals = this.subTotals + (product.price * product.quantity);
      this.subTotals = Number(parseFloat(String(this.subTotals)).toFixed(2));
      this.subQuantity = this.subQuantity + product.quantity;
    }
  }

  ngOnInit(): void {
    this.myForm = this.fb.group({
      fName: ['', Validators.required],
      lName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      CC: ['', [Validators.required, Validators.minLength(16), Validators.maxLength(16)]],
      expMonth: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(2)]],
      expYear: ['', [Validators.required, Validators.minLength(2),Validators.maxLength(2)]],
      securityCode: ['', [Validators.required, Validators.minLength(3),Validators.maxLength(3)]],
      address:['', [Validators.required]],
      zip:['', [Validators.required,Validators.minLength(5),Validators.maxLength(5)]],
      city:['', [Validators.required]],
      state:['', [Validators.required]],
      country:['USA', [Validators.required]],

    });
    this.productService.getCart().pipe(filter(cart => !!cart), takeUntil(this.componentDestroyed$))
      .subscribe(cartItems => {
        this.checkoutItems = cartItems
        this.subTotal();
      });
    this.productService.getProducts().pipe(filter(products => !!products), takeUntil(this.componentDestroyed$))
      .subscribe(allProducts => {
        this.product = allProducts;
      });
      this.userService.getCurrentUser().pipe(filter(user => !!user), takeUntil(this.componentDestroyed$))
      .subscribe(user =>{
        this.user = user;
      });



  }
  async onSubmit(form: FormGroup) {
    this.productService.getCart().subscribe(async (cartItems) => {
      console.log('cartitems', cartItems, '\n products', this.product);
      let stockProduct = this.getNewStockProducts(cartItems, this.product);
      console.log('stockProduct', stockProduct);
      for (let item of stockProduct) {
        this.productService.saveStock(item).subscribe(response => {
          console.log('response', response);
        });
      }
    });
    this.productService.saveUserCheckout().subscribe((purchased: any) => {
      sessionStorage.clear();
      this.productService.productCart = [];
      this.productService._productCart.next([]);
      this.router.navigate(['/confirm']);
      this.productService.checkout = true;
    })
  }

  getNewStockProducts(cart: any[], products: any[])
  {
    let temp: Product[] = [];
    cart = JSON.parse(JSON.stringify(cart));
    products = JSON.parse(JSON.stringify(products));
    for(let item of cart)
    {
      for(let product of products)
      {
        if(item.id === product.id)
        {
          product.quantity = product.quantity - item.quantity;
          temp.push(product);
        }
      }
    }
    return temp;

  }

  goBack(): void {
    console.log("works")
    this.location.back();
  }

}
