import { HttpClient, HttpHeaders } from '@angular/common/http';
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

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  getLessons(): Observable<Lesson[]> {
    this.messageService.add('LessonService: fetched lessons')
    return this.http.get<Lesson[]>(this.lessonsURL, this.httpOptions);
  }

  getLesson(id: Number): Observable<Lesson>{
    this.messageService.add('LessonService: fetched lesson with id=' + id);
    const url = `${this.lessonsURL}/${id}`;
    return this.http.get<Lesson>(url, this.httpOptions);
  }

  deleteLesson(id: Number) {
    const url = `${this.lessonsURL}/${id}`;
    return this.http.delete<Lesson>(url, this.httpOptions);
  }
}
