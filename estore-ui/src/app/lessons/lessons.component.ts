import { Component, OnInit } from '@angular/core';
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

  constructor(private lessonService: LessonService,
    private messageService: MessageService) { }

  ngOnInit(): void {
    this.getLessons();
  }

  getLessons() {
    this.lessonService.fetchLessons().subscribe(lessons => this.lessons = lessons);
  }

  onSelect(lesson: Lesson) {
    this.messageService.add(`LessonsComponent: Selected lesson id=${lesson.id}`)
    console.log("Selected lesson has id=" + lesson.id);
    this.selectedLesson = lesson;
    //if(user is not logged in or is admin this action cannot be allowed)
    //TODO
  }
}
