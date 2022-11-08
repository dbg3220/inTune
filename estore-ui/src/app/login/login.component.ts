import { Component, OnInit, AfterViewChecked } from '@angular/core';
import { User } from '../user';
import { UserService } from '../user.service';
import { UsernameValidator } from '../custom-username.validator';
import {
  FormGroup,
  FormBuilder,
  Validators,
} from "@angular/forms";
import { filter, Subject, takeUntil } from 'rxjs';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
// import { CustomUserValidator } from "../shared/custom-email.validator";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  login!: FormGroup;
  users: User[] = [];
  componentDestroyed$ = new Subject(); // tracks components lifecycle for subscription of the observable, component will automatically repaint if data changes
  exists: boolean = false;
  message: string = "";
  user: User| undefined;
  created: boolean = false;


  onLogin() {
    const username = this.login.get('username')?.value;
    this.user = username;
    console.log(this.login.value);
    for (let user of this.users) {
      if (user.username === username) {
        this.exists = true;
        console.log("exists");
        this.userService.setCurrentUser(username);
        return
      }
    }
      console.log("does not exist");
      this.exists = false;
      this.userService.addUser({ username } as User)
    .subscribe(user => {
      this.users.push(user);
    });
    this.userService.setCurrentUser(username);
    console.log(this.login.value + "added");
    this.exists = true;
    this.created = true;
    this.message = "It seems you weren't registered. We have added you as a user. Welcome " + username;
  }

  onLogout() {
    this.userService.setCurrentUser(undefined);
    this.user = undefined;
    this.exists = false;
  }

  // onSignup() {
  //   const username = this.login.get('username')?.value;
  //   console.log(this.login.value);
  //   for (let user of this.users) {
  //     if (user.username === username) {
  //       this.exists = true;
  //       console.log("exists");
  //     }
  //     else {
  //   this.userService.addUser({ username } as User)
  //     .subscribe(user => {
  //       this.users.push(user);
  //     });
  //   console.log(this.login.value + "added");
  //   this.exists = true;
  //     }
  // }
  // }

  get username(){
    return this.login.get('username');
  }

  constructor(
    private userService: UserService,
    private fb: FormBuilder,
    // private usernameValidator: UsernameValidator
  ) {

  }

  ngOnInit(): void {
    this.userService.getUsers().pipe(filter(users => !!users)).subscribe((users: User[]) => this.users = users);
    this.login = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(20)]]
    });
    this.userService.getCurrentUser().pipe(filter(user => !!user))
      .subscribe(user =>{
        this.user = user;
      });
      if (this.user) {
        this.exists = true;
      }``
  }


}
