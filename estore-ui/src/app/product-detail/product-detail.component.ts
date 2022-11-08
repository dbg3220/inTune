import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';


import { ProductService } from '../product.service';
import { Product } from '../product';
import { MessengerService } from '../messenger.service';
import { UserService } from '../user.service';
import { filter, takeUntil } from 'rxjs';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Review } from '../review';
import { User } from '../user';




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
  form!: FormGroup;
  isLoggedIn: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private productService: ProductService,
    private location: Location,
    private msg: MessengerService,
    private userService: UserService,
    private fb: FormBuilder,
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
    this.form = this.fb.group({
      reviewUsername: ['', Validators.required],
      rating: ['', Validators.required],
      description: ['', Validators.required]
    });
    
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


  toNumber(price: string): number {
    return Number(price);
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

  handleAddReview(reviewUsername: String, rating: number, description: String){
    this.product.reviews.push({reviewUsername, rating, description} as Review)
    this.productService.updateProduct(this.product);
    this.save();
  }

  loggedIn(){
    if(this.user){
      return true;
    }
    return false;
  }

  averageRating(){
    let total = 0;
    let count = 0;
    for (let review of this.product.reviews){
      total += review.rating;
      count++;
    }
    var multiplier = Math.pow(10, 2);
    return Math.round(total/count * multiplier) / multiplier;
  }
}
