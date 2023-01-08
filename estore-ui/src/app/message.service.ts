import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class MessageService {
  private messages: string[] = [];

  add(message: string) {
    this.messages.push(message);
    console.log(message);
  }

  clear() {
    this.messages = [];
  }

  getAll(){
    return this.messages;
  }
}