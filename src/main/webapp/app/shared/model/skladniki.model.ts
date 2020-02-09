import { SkladnikiJednostkaEnum } from 'app/shared/model/enumerations/skladniki-jednostka-enum.model';
import { SkladnikiKategoriaEnum } from 'app/shared/model/enumerations/skladniki-kategoria-enum.model';

export interface ISkladniki {
  id?: number;
  nazwa?: string;
  zdjecieContentType?: string;
  zdjecie?: any;
  jednostka?: SkladnikiJednostkaEnum;
  kategoria?: SkladnikiKategoriaEnum;
  kalorieSto?: number;
  kalorieJednostka?: number;
}

export class Skladniki implements ISkladniki {
  constructor(
    public id?: number,
    public nazwa?: string,
    public zdjecieContentType?: string,
    public zdjecie?: any,
    public jednostka?: SkladnikiJednostkaEnum,
    public kategoria?: SkladnikiKategoriaEnum,
    public kalorieSto?: number,
    public kalorieJednostka?: number
  ) {}
}
