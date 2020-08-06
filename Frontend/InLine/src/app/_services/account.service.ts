import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { User } from '../_objects/user';
import { AccountTypes } from '../_objects/account-types';
import {Router} from '@angular/router';
import {map} from 'rxjs/operators';
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

  // TODO: Implement AccountService.register(...)
  register(username: string, password: string, isAdmin: AccountTypes): void { // Should probably return ifSuccess
    const u = new User();
    u.username = username === undefined ? 'Undefined' : username;
    u.accessLevel = isAdmin;

    localStorage.setItem('user', JSON.stringify(u));
    this.userSubject.next(u);

    /*this.http.post<User>(`${environment.apiURL}/register/submit`, {username, password})
      .pipe(map(user => {
        localStorage.setItem('user', JSON.stringify(user));
        this.userSubject.next(user);
        return user;
      }));*/
  }

  // TODO: on implementing token, fix this method: AccountService.login(...)
  login(username: string, password: string): void {
    this.register(username, password, AccountTypes.ADMIN);
  }

  logout(): void {
    localStorage.removeItem('user');
    this.userSubject.next(null);
    this.router.navigate(['']);
  }

  public get userValue(): User {
    return this.userSubject.value;
  }
}
