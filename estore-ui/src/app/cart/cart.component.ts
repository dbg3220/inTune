import {Component, OnInit} from '@angular/core';
import {MessengerService} from '../messenger.service';
import {Product} from '../product';
import {filter, Subject, takeUntil} from "rxjs";
import {ProductService} from "../product.service";
import {Router} from "@angular/router";
import {Location} from "@angular/common";

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']

})
export class CartComponent implements OnInit {

  cartItems: Product[] = [];
  componentDestroyed$ = new Subject();
  subTotals: number = 0;
  subQuantity: number = 0;

  constructor(
    private msg: MessengerService,
    private productService: ProductService,
    private router: Router,
    private location: Location
  ) {}

  removeProductCart(product: Product) {
    this.productService.removeToCart(product);
    // when removed, set subTotals and subQuantity to 0 to refresh the amount
    // Call subTotal() to get the new subTotals and subQuantityS
    this.subTotals = 0;
    this.subQuantity = 0;
    this.subTotal();

  }

  subTotal() {
    for (let product of this.cartItems) {
      this.subTotals = this.subTotals + (product.price * product.quantity);
      this.subTotals = Number(parseFloat(String(this.subTotals)).toFixed(2));
      this.subQuantity = this.subQuantity + product.quantity;
    }
  }

  ngOnInit(): void {
    this.productService.getCart().pipe(filter(cart => !!cart), takeUntil(this.componentDestroyed$))
      .subscribe(cartItems => {
        this.cartItems = cartItems
        this.subTotal();
      });

    this.msg.getMsg().subscribe(product => {
      console.log(product)
    })
  }

  routeToCheckoutComponent() {
    this.router.navigate(['/checkout']);
  }
  goBack(): void {
    console.log("works")
    this.location.back();
  }

}
