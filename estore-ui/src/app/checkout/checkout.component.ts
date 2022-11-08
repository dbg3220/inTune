import { Component, OnInit } from '@angular/core';
import {CartComponent} from "../cart/cart.component";
import {ProductService} from "../product.service";
import {Location} from "@angular/common";
import {Product} from "../product";
import {filter, Subject, takeUntil} from "rxjs";
import {Router} from "@angular/router";
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

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

  constructor(
    private productService: ProductService,
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
      CC: ['', [Validators.required, Validators.minLength(9), Validators.maxLength(9)]],
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



  }
  onSubmit(form: FormGroup) {
    let obj = {'fName':form.value.fName,'lName':form.value.lName,'Email':form.value.email,
      'CC':form.value.CC, 'expMonth':form.value.expMonth, 'expYear':form.value.expYear,
      'securityCode':form.value.securityCode, 'address':form.value.address, 'zip':form.value.zip,
      'city':form.value.city,'state':form.value.state,'country':form.value.country }

    console.log('Valid?', form.valid); // true or false
    console.log('Name', obj);

  }

  goBack(): void {
    console.log("works")
    this.location.back();
  }
  routeToCheckoutComponent() {
    this.router.navigate(['/confirm']);
  }

}
