import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { RegisterEntryComponent } from './register/register.component';
import { LoginEntryComponent} from './login/login.component';

const routes: Routes = [
  { path: 'register', component: RegisterEntryComponent },
  { path: 'login', component: LoginEntryComponent },
  // { path: 'account', component: },
  // { path: 'search', component: },
  // { path: '**', component: PageNotFoundComponent}
];

@NgModule({
  declarations: [],
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule { }
