import { Component } from '@angular/core';
import { User } from './_objects/user';
import {AccountService} from './_services/account.service';
import {AccountTypes} from './_objects/account-types';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'InLine';
  user: User;
  AccountTypes = AccountTypes;
  // user = new User();

  constructor(private accountService: AccountService){
    // this.user.username = 'bob';
    // this.user.accessLevel = false;
    accountService.user.subscribe(user => {
      this.user = user;
    });
  }

  logout(): void {
    this.accountService.logout();
    // this.user = undefined; // Probably should be an observable / promise of the logout function
  }

  search(): void {
    console.log('A search has been entered, but nothing is done to it.');
  }
}
