import { Component, OnInit } from '@angular/core';
import { MessengerService } from '../messenger.service';
import { Product } from '../product';
@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {

  cartItems : Product[] = [
    
  ];

  constructor(private msg: MessengerService) { }

  ngOnInit(): void{


    this.msg.getMsg().subscribe( product => {
      console.log(product)

  })

}
}
