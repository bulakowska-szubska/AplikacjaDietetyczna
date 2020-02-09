import { ISkladniki } from 'app/shared/model/skladniki.model';
import { IUser } from 'app/core/user/user.model';

export interface IPrzepisSkladniki {
  id?: number;
  ilosc?: number;
  kalorieIlosc?: number;
  skladniki?: ISkladniki;
  user?: IUser;
}

export class PrzepisSkladniki implements IPrzepisSkladniki {
  constructor(
    public id?: number,
    public ilosc?: number,
    public kalorieIlosc?: number,
    public skladniki?: ISkladniki,
    public user?: IUser
  ) {}
}
