import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { Lesson } from '../lesson';
import { LessonService } from '../lesson.service';
import { MessageService } from '../message.service';
import { User } from '../user';
import { UserService } from '../user.service';

@Component({
  selector: 'app-lessons',
  templateUrl: './lessons.component.html',
  styleUrls: ['./lessons.component.css']
})
export class LessonsComponent implements OnInit {

  lessons: Lesson[] = [];
  currentUser?: String;
  isAdmin: boolean = false;
  selectedLesson?: Lesson;
  promptLogin: boolean = false;

  constructor(private lessonService: LessonService,
    private messageService: MessageService, private userService: UserService, 
    private router: Router) {
      router.events.subscribe(
        (event) => {
          if(event instanceof NavigationEnd){
            this.promptLogin = false;
          }
        }
      )
     }

  ngOnInit(): void {
    this.messageService.add('LessonComponent has been initialized');
    this.getLessons();
    this.getCurrentUser();
  }

  getCurrentUser() {
    this.userService.getCurrentUser().subscribe(user => this.currentUser = user);
    if(this.currentUser == 'admin'){
      this.isAdmin = true;
    } else {
      this.isAdmin = false;
    }
  }

  availableLessons() {
    return this.lessons.filter(lesson => lesson.isFull == false);
  }

  schedule(lesson: Lesson) {
    this.messageService.add(`LessonsComponent: Selected lesson id=${lesson.id}`)
    console.log("Selected lesson has id=" + lesson.id);
    this.selectedLesson = lesson;

  }

  toNumber(param: String): number {
    return Number(param);
  }

  getLessons() {
    this.messageService.add('LessonComponent: subscribed to lesson service')
    this.lessonService.getLessons().subscribe(lessons => this.lessons = lessons);
  }

  addLesson(category: String, instructor: String, weekday: String,
            startTime: Number, price: Number, name: String) {
    this.messageService.add(`LessonComponent: Added new lesson name=${name}`)
    name = name.trim();
    this.lessonService.addLesson({ category, instructor, weekday, startTime, price, name } as Lesson)
              .subscribe(lesson => {
                this.lessons.push(lesson)
              });
  }

  deleteLesson(lesson: Lesson){
    this.messageService.add(`LessonComponent: Deleted lesson id=${lesson.id}`)
    this.lessons = this.lessons.filter(l => l!== lesson);
    this.lessonService.deleteLesson(lesson.id).subscribe();
  }
}
