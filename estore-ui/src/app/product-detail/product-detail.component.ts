import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';


import { ProductService } from '../product.service';
import { Product } from '../product';
import { MessengerService } from '../messenger.service';
import { UserService } from '../user.service';
import { filter, takeUntil } from 'rxjs';




@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrls: [ './product-detail.component.css' ]
})
export class ProductDetailComponent implements OnInit {
  product!: Product;
  added: boolean = false;
  user: string = "";
  isAdmin: boolean = false;
  deleted: boolean = false;


  constructor(
    private route: ActivatedRoute,
    private productService: ProductService,
    private location: Location,
    private msg: MessengerService,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.getProduct();
    this.userService.getCurrentUser().pipe(filter(user => !!user))
    .subscribe(user =>{
      this.user = user;
    });
    if (this.user == 'admin'){
      this.isAdmin = true;
    }
  }

  getProduct(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.productService.getProduct(id)
      .subscribe(product => this.product = product);
  }

  goBack(): void {
    console.log("works")
    this.location.back();
  }

  save(): void{
    if (this.product){
      this.productService.updateProduct(this.product).subscribe(() => this.goBack)
    }
  }

  handleDelete(){
    this.productService.deleteProduct(this.product.id).subscribe(() => this.goBack())
    this.deleted = true;
  }

  handleAddToCart(){
    console.log("works")
    this.productService.addToCart(this.product);
    this.msg.sendMsg(this.product)
    this.added = true;
  }
}
