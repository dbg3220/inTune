import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Lesson } from './lesson';
import { MessageService } from './message.service';

@Injectable({
  providedIn: 'root'
})
export class LessonService {

  constructor(private messageService: MessageService, private http: HttpClient) { }

  private lessonsURL = 'http://localhost:8080/lessons';  // URL to web api

  fetchLessons(): Observable<Lesson[]> {
    this.messageService.add('LessonService: fetched lessons')
    return this.http.get<Lesson[]>(this.lessonsURL);
  }
}
