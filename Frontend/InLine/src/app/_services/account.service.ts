import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable, of, throwError} from 'rxjs';
import { User } from '../_objects/user';
import { AccountTypes } from '../_objects/account-types';
import {Router} from '@angular/router';
import {catchError, map, tap, retry} from 'rxjs/operators';
import {environment} from '../../environments/environment';
import {HttpClient, HttpHeaders} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})

export class AccountService {

  private userSubject: BehaviorSubject<User>;
  public user: Observable<User>;

  constructor(
    private router: Router,
    private http: HttpClient
  ) {
    this.userSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('user')));
    this.user = this.userSubject.asObservable();
  }

  register(user: User): Observable<User> {
    // Use as 3rd param in http request
    const httpHeader = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json',
        Authorization: 'my-auth-token'
      })
    };

    return this.http.post<User>(`${environment.backendURL}/register/submit`, user.getAsJSON())
      .pipe(
        tap((u: User) => {
          console.log(`Added user by name of '${u.first_name}'`);
          localStorage.setItem('user', JSON.stringify(u));
          this.userSubject.next(u);
          return u;
        }),
        catchError(this.handleError<any>('register user'))
      );
  }

  login(email: string, password: string): Observable<User> {
    return this.http.post<User>(`${environment.backendURL}/login/submit`, {email, password})
      .pipe(
        tap((u: User) => {
          console.log(`Added user by name of '${u.first_name}'`);
          localStorage.setItem('user', JSON.stringify(u));
          this.userSubject.next(u);
          return u;
        }),
        catchError(this.handleError<any>('login user'))
      );
  }

  logout(): void {
    localStorage.removeItem('user');
    this.userSubject.next(null);
    this.router.navigate(['']);
  }

  private handleError<T>(operation = 'operation', result?: T): (error: any) => Observable<T>{
    return (error: any): Observable<T> => {
      console.error(error);
      console.log(`${operation} failed: ${error.message}`);
      return of(result as T);
    };
  }

  public get userValue(): User {
    return this.userSubject.value;
  }
}
