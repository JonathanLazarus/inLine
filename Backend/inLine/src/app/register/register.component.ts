import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { AbstractControl, FormBuilder, FormGroup } from '@angular/forms';
import { AccountService } from '../_services/account.service';
import { AccountTypes } from '../_objects/account-types';
import {User} from '../_objects/user';

@Component({
  template: ''
})

export class RegisterEntryComponent {
  constructor(public dialog: MatDialog, private router: Router,
              private route: ActivatedRoute) {
    this.openDialog();
  }
  openDialog(): void {
    const dialogRef = this.dialog.open(RegisterComponent, {
      width: '25%'
    });
    dialogRef.afterClosed().subscribe(result => {
      this.router.navigate(['../'], {relativeTo: this.route});
    });
  }
}

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  form: FormGroup;
  hide = true;
  AccountTypes = AccountTypes;
  accountType: AccountTypes;

  constructor(
    private accountService: AccountService,
    private formBuilder: FormBuilder,
    public dialogRef: MatDialogRef<RegisterComponent>
  ) { }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      first_name: [''],
      last_name: [''],
      password: [''],
      email: [''],
      contactNumber: ['']
    });
    this.accountType = AccountTypes.USER;
  }

  get f(): { [p: string]: AbstractControl } { return this.form.controls; }

  onSubmit(): void {
    this.accountService.register(new User(
      this.f.email.value,
      this.accountType,
      this.f.first_name.value,
      this.f.last_name.value,
      undefined,
      undefined
    ), this.f.password.value).subscribe(() => this.dialogRef.close());
  }

}
