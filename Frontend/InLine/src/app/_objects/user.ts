import {Store} from './store';
import {AccountTypes} from './account-types';

export class User {
  constructor(
    public email: string,
    public password: string,

    // tslint:disable-next-line:variable-name
    public account_type: AccountTypes,

    // tslint:disable-next-line:variable-name
    public first_name: string,

    // tslint:disable-next-line:variable-name
    public last_name: string,

    public storeList: Store[],
    public token: string
  ){}

  public getAsJSON(): JSON {
    return {
      email: this.email,
      password: this.password,
      account_type: (this.account_type === AccountTypes.ADMIN) ? 'ADMIN' : 'USER', // TODO: Fix the enum!
      first_name: this.first_name,
      last_name: this.last_name
    } as unknown as JSON;
  }
}
