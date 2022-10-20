import { Component, OnInit, AfterViewChecked } from '@angular/core';
import { User } from '../user';
import { UserService } from '../user.service';
import { UsernameValidator } from '../custom-username.validator';
import {
  FormGroup,
  FormBuilder,
  Validators,
} from "@angular/forms";

// import { CustomUserValidator } from "../shared/custom-email.validator";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  login!: FormGroup;

  get username(){
    return this.login.get('username');
  }

  constructor(
    private fb: FormBuilder,
    private usernameValidator: UsernameValidator) { }

  ngOnInit(): void {
    this.createForm();
  }

  createForm() {
    this.login = this.fb.group({
      username: ["", [Validators.required], [this.usernameValidator.existingUsernameValidator()]],
    });
  }

}
