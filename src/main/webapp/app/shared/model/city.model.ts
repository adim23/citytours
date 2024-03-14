import { ITour } from 'app/shared/model/tour.model';
import { IPlace } from 'app/shared/model/place.model';

export interface ICity {
  id?: number;
  code?: string | null;
  name?: string;
  tours?: ITour[] | null;
  places?: IPlace[] | null;
}

export const defaultValue: Readonly<ICity> = {};
