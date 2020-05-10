import { IClub } from 'app/shared/model/club.model';
import { IReferent } from 'app/shared/model/referent.model';
import { IPlateau } from 'app/shared/model/plateau.model';

export interface IInscription {
  id?: number;
  nombreEquipe?: number;
  club?: IClub;
  referent?: IReferent;
  plateau?: IPlateau;
}

export const defaultValue: Readonly<IInscription> = {};
