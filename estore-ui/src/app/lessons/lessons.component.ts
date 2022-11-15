import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, NavigationEnd, NavigationStart, Router } from '@angular/router';
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

  /** The current lesson array for the estore */
  lessons: Lesson[] = [];
  /** The current user logged into the website, null if no user is logged in */
  currentUser?: User;
  /** A boolean that tells if the admin is logged in */
  isAdmin: boolean = false;
  /** Turns on a component of the page that prompts the user to log in */
  promptLogin: boolean = false;
  /** An identifier variable for this class, is turned on and off when getLessons(), getCurrentUser() starts and ends */
  refreshDone: boolean = true;
  /** The possible days that can be entered for a lesson */
  potentialDays: String[] = [ 'MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY' ];

  /** Public constructor for creating this ts component */
  constructor(private lessonService: LessonService,
    private userService: UserService, 
    private router: Router) {
      router.events.subscribe(
        (event) => {
          if(event instanceof NavigationEnd){
            this.promptLogin = false;
          }
          if(event instanceof NavigationStart){
            this.getCurrentUser();
            this.getLessons();
          }
        }
      )
  }

  /** Initializes the lesson page by getting the current stock of lessons and the current user logged in */
  ngOnInit(): void {
    this.getLessons();
    this.getCurrentUser();
  }

  /** Retrieves the current user of the page, using the user service */
  getCurrentUser() {
    this.userService.getCurrentUser().subscribe(user => {
      this.currentUser = user;
      if(this.currentUser){
        this.isAdmin = (this.currentUser.username == 'admin');
      }
    });
  }

  /** Organizes a sub-array of lessons that are not booked, shows all lessons to the admin */
  availableLessons(): Lesson[] {
    if(!this.isAdmin) {
      return this.sort(this.lessons.filter(lesson => !this.isFull(lesson)));
    } else {
      return this.sort(this.lessons);
    }
  }

  /** Organizes a sub-array lessons that are scheduled by the current user*/
  scheduledLessons(): Lesson[] {
    if(this.currentUser){
      var result: Lesson[] = this.lessons.filter(lesson => lesson.userID == this.currentUser!.id);
      return this.sort(result);
    }else{
      return [];
    }
  }

  /** Sorts and returns a given array of lessons by day of the week and time of day(MOND->FRI,9->5)  */
  sort(lessons: Lesson[]): Lesson[] {
     var result: Lesson[] = lessons.sort((lesson1, lesson2) => {
      var day1 = this.dayToValue(lesson1.weekday);//The integer value of lesson1's weekday
      var day2 = this.dayToValue(lesson2.weekday);//The integer value of lesson2's weekday
      if(day1 > day2){
        return 1;
      } else if(day1 < day1){
        return -1;
      } 
      return 0;
    });
    console.log(result);
    return result;
  }

  /** Schedules a lesson for the current user */
  scheduleLesson(lesson: Lesson) {
    if(this.currentUser && !this.isAdmin){
      lesson.isFull = true;
      lesson.userID = this.currentUser!.id;
      this.lessonService.updateLesson(lesson).subscribe(() => {
        this.getLessons();
      });
    } else {
      this.promptLogin = true;
    }
  }

  // LESSON SERVICE METHODS

  /** Refreshes the page with the current lessons available */
  getLessons() {
    this.refreshDone = false;
    this.lessonService.getLessons().subscribe(lessons => {
      this.lessons = lessons;
      this.refreshDone = true;
    });
  }

  /** Adds a lesson to the inventory */
  addLesson(category: String, instructor: String, weekday: String,
            startTime: Number, price: Number, name: String) {
    name = name.trim();
    this.addLessonHelp(-1, false, category, instructor, weekday, startTime, -1, price, name);
  }

  /** Helper method to lesson that calls the lesson service */
  private addLessonHelp(id: Number, isFull: boolean, category: String, 
                instructor: String, weekday: String, startTime: Number, 
                userID: Number, price: Number, name: String) {
    var newLesson = { id, isFull, category, instructor, weekday, startTime, userID, price, name } as Lesson;
    this.lessonService.addLesson(newLesson).subscribe(() => {
      this.getLessons();
    });
  }

  /** Deletes a lesson using the LessonService */
  deleteLesson(lesson: Lesson){
    this.lessonService.deleteLesson(lesson.id).subscribe(() => {
      this.lessons = this.lessons.filter(l => l !== lesson);
    });
  }

  // UTILITY FUNCTIONS

  /** Translates a string to a number */
  toNumber(param: String): number {
    return Number(param);
  }

  /** Tells if a lesson is free, not scheduled by a user */
  private isFull(lesson: Lesson): boolean {
    return lesson.isFull;
  }

  /** Assigns a weekday value to an integer so it can be used to sort lessons, if no day found returns -1 */
  private dayToValue(day: String): Number {
    for(let i = 0; i < this.potentialDays.length; i++) {
      if(day == this.potentialDays[i]){
        return i + 1;
      }
    }
    return -1;
  }
}