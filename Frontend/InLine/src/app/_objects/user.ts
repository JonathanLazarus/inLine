import {Store} from './store';
import {AccountTypes} from './account-types';

export class User {
  username: string;
  token: string;
  storeList: Store[];

  // TODO: Temp[Dev] - remove upon implementation: token
  accessLevel: AccountTypes;
}
