import { IClub } from 'app/shared/model/club.model';

export interface ILogoClub {
  id?: number;
  logoContentType?: string;
  logo?: any;
  club?: IClub;
}

export const defaultValue: Readonly<ILogoClub> = {};
