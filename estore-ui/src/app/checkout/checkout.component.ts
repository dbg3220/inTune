import { Component, OnInit } from '@angular/core';
import {CartComponent} from "../cart/cart.component";
import {ProductService} from "../product.service";
import {Location} from "@angular/common";
import {Product} from "../product";
import {filter, Subject, takeUntil} from "rxjs";
import {Router} from "@angular/router";

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

  constructor(
    private productService: ProductService,
    private location: Location,
    private router: Router

  ) { }

  subTotal() {
    for (let product of this.checkoutItems) {
      this.subTotals = this.subTotals + (product.price * product.quantity);
      this.subTotals = Number(parseFloat(String(this.subTotals)).toFixed(2));
      this.subQuantity = this.subQuantity + product.quantity;
    }
  }

  ngOnInit(): void {
    this.productService.getCart().pipe(filter(cart => !!cart), takeUntil(this.componentDestroyed$))
      .subscribe(cartItems => {
        this.checkoutItems = cartItems
        this.subTotal();
      });


  }

  goBack(): void {
    console.log("works")
    this.location.back();
  }
  routeToCheckoutComponent() {
    this.router.navigate(['/confirm']);
  }

}
