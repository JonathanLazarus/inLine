import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { AbstractControl, FormBuilder, FormGroup } from '@angular/forms';
import { AccountService } from '../_services/account.service';
import { AccountTypes } from '../_objects/account-types';

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
      username: [''],
      password: [''],
      email: [''],
      contactNumber: ['']
    });
  }

  get f(): { [p: string]: AbstractControl } { return this.form.controls; }

  onSubmit(): void {
    console.log(this.form.value, AccountTypes[this.accountType]);

    this.accountService.register(this.f.username.value, this.f.password.value, this.accountType);
    this.dialogRef.close();
  }

}
