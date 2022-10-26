import { Component, OnInit } from '@angular/core';
import { MessengerService } from '../messenger.service';
import { Product } from '../product';
import {filter, Subject, takeUntil} from "rxjs";
import {ProductService} from "../product.service";
@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']

})
export class CartComponent implements OnInit {

  cartItems : Product[] = [];
  componentDestroyed$ = new Subject();

  constructor(private msg: MessengerService,private productService: ProductService) { }

  ngOnInit(): void{
    this.productService.getCart().pipe(filter(cart => !!cart), takeUntil(this.componentDestroyed$))
      .subscribe(cartItems => this.cartItems = cartItems);


    this.msg.getMsg().subscribe( product => {
      console.log(product)

  })

}
}
