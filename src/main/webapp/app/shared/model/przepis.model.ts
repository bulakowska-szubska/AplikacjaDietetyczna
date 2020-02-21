import { IUser } from 'app/core/user/user.model';
import { TypPrzepisuEnum } from 'app/shared/model/enumerations/typ-przepisu-enum.model';

export interface IPrzepis {
  id?: number;
  nazwa?: string;
  typPrzepisu?: TypPrzepisuEnum;
  zdjecieContentType?: string;
  zdjecie?: any;
  opis?: string;
  kalorieSuma?: number;
  user?: IUser;
}

export class Przepis implements IPrzepis {
  constructor(
    public id?: number,
    public nazwa?: string,
    public typPrzepisu?: TypPrzepisuEnum,
    public zdjecieContentType?: string,
    public zdjecie?: any,
    public opis?: string,
    public kalorieSuma?: number,
    public user?: IUser
  ) {}
}
