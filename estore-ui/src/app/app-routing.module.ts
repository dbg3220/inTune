
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProductsComponent } from './products/products.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ProductDetailComponent } from './product-detail/product-detail.component';
import { LoginComponent } from './login/login.component';
import { CartComponent } from './cart/cart.component';
import {CheckoutComponent} from "./checkout/checkout.component";
<<<<<<< HEAD
import {ConfirmComponent} from "./confirm/confirm.component";
=======
import { PagenotfoundComponent } from './pagenotfound/pagenotfound.component';
>>>>>>> 0d042acfd1ac884288c4e8af4a66151472e848dc
import { LessonsComponent } from './lessons/lessons.component';

const routes: Routes = [
  { path: 'products', component: ProductsComponent },
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'detail/:id', component: ProductDetailComponent},
  { path: 'login', component: LoginComponent},
  { path: 'cart', component: CartComponent},
  { path: 'checkout', component: CheckoutComponent },
<<<<<<< HEAD
  { path: 'confirm', component: ConfirmComponent},
=======
  { path: '**', pathMatch: 'full', 
        component: PagenotfoundComponent },
>>>>>>> 0d042acfd1ac884288c4e8af4a66151472e848dc
  { path: 'lessons', component: LessonsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }