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
  subTotals: number = 0;
  subQuantity: number =0;

  constructor(private msg: MessengerService,private productService: ProductService) { }

  removeProductCart(product: Product)
  {
    this.productService.removeToCart(product);
  }

  subTotal()
  {
    for(let product of this.cartItems)
    {
      this.subTotals = this.subTotals + (product.price * product.quantity);
      this.subQuantity = this.subQuantity + product.quantity;
    }
  }


  ngOnInit(): void{
    this.productService.getCart().pipe(filter(cart => !!cart), takeUntil(this.componentDestroyed$))
      .subscribe(cartItems =>{
        this.cartItems = cartItems
        this.subTotal();
      });

    this.msg.getMsg().subscribe( product => {
      console.log(product)

  })

}
}
