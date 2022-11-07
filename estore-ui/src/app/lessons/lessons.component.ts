import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { Lesson } from '../lesson';
import { LessonService } from '../lesson.service';
import { MessageService } from '../message.service';

@Component({
  selector: 'app-lessons',
  templateUrl: './lessons.component.html',
  styleUrls: ['./lessons.component.css']
})
export class LessonsComponent implements OnInit {

  lessons: Lesson[] = [];
  selectedLesson?: Lesson;
  promptLogin: boolean = false;

  constructor(private lessonService: LessonService,
    private messageService: MessageService, private aRoute: ActivatedRoute, 
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
  }

  getLessons() {
    this.messageService.add('LessonComponent: subscribed to lesson service')
    this.lessonService.getLessons().subscribe(lessons => this.lessons = lessons);
  }

  availableLessons() {
    return this.lessons.filter(lesson => lesson.isFull == false);
  }

  onSelect(lesson: Lesson) {
    this.messageService.add(`LessonsComponent: Selected lesson id=${lesson.id}`)
    console.log("Selected lesson has id=" + lesson.id);
    this.selectedLesson = lesson;
    const currentUserID = 1;//TODO replace with function that keeps track of current user
    const isAdmin = true;//TODO replace with function that keeps rack of admin status
    if(false && !isAdmin){//Adjust if statement with new functions

    } else {
      this.promptLogin = true;
    }
  }

  delete(lesson: Lesson){
    this.messageService.add(`LessonComponent: Deleted lesson id=${lesson.id}`)
    this.lessons = this.lessons.filter(l => l!== lesson);
    this.lessonService.deleteLesson(lesson.id).subscribe();
  }
}
