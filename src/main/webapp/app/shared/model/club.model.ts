import { ILogoClub } from 'app/shared/model/logo-club.model';
import { IStade } from 'app/shared/model/stade.model';
import { IUser } from 'app/shared/model/user.model';
import { ICategorie } from 'app/shared/model/categorie.model';

export interface IClub {
  id?: number;
  nom?: string;
  adresse?: string;
  telephone?: string;
  email?: string;
  logoClub?: ILogoClub;
  stades?: IStade[];
  user?: IUser;
  categories?: ICategorie[];
}

export const defaultValue: Readonly<IClub> = {};
