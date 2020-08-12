import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { AbstractControl, FormBuilder, FormGroup } from '@angular/forms';
import { AccountService } from '../_services/account.service';
import {User} from '../_objects/user';

@Component({
  template: ''
})

export class LoginEntryComponent {
  constructor(public dialog: MatDialog, private router: Router,
              private route: ActivatedRoute) {
    this.openDialog();
  }
  openDialog(): void {
    const dialogRef = this.dialog.open(LoginComponent, {
      width: '25%'
    });
    dialogRef.afterClosed().subscribe(result => {
      this.router.navigate(['../'], {relativeTo: this.route}).then(r => {});
    });
  }
}

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  form: FormGroup;
  hide = true;

  constructor(
    private accountService: AccountService,
    private formBuilder: FormBuilder,
    public dialogRef: MatDialogRef<LoginComponent>
  ) { }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      email: [''],
      password: ['']
    });
  }

  get f(): { [p: string]: AbstractControl } { return this.form.controls; }

  onSubmit(): void {
    this.accountService.login(this.f.email.value, this.f.password.value).subscribe(() => this.dialogRef.close());
  }

  close(): void {
    this.dialogRef.close();
  }
}
