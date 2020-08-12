import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable, of} from 'rxjs';
import {User} from '../_objects/user';
import {AccountTypes} from '../_objects/account-types';
import {Router} from '@angular/router';
import {catchError, tap} from 'rxjs/operators';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';

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

  register(user: User, password: string): Observable<User> {
    return this.http.post<string>(`${environment.backendURL}/register/submit`, user.getAsJSON(password))
      .pipe(
        tap((JWToken: string) => {
          user.token = JWToken;

          if (user.first_name === undefined) { return false; } // TODO: Part of error checking.

          localStorage.setItem('user', JSON.stringify(user));
          this.userSubject.next(user);

          console.log('Added user object:', user);
          return true;
        }),
        catchError(this.handleError<any>('register user'))
      );
  }

  login(email: string, password: string): Observable<User> {
    return this.http.post<string>(`${environment.backendURL}/login/submit`, {email, password})
      .pipe(
        tap((response: any) => {
          const registeredUser = new User(
            email,
            response.account_type,
            response.first_name,
            response.last_name,
            response.storeList,
            response.token
          );
          if (registeredUser.first_name === undefined) { return false; } // TODO: Part of error checking.

          localStorage.setItem('user', JSON.stringify(registeredUser));
          this.userSubject.next(registeredUser);

          console.log('Added user object:', registeredUser);
          return true;
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
