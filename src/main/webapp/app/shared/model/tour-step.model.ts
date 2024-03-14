import { ITour } from 'app/shared/model/tour.model';
import { IPlace } from 'app/shared/model/place.model';

export interface ITourStep {
  id?: number;
  code?: string;
  stepOrder?: number;
  waitTime?: number | null;
  driveTime?: number | null;
  tour?: ITour | null;
  place?: IPlace | null;
}

export const defaultValue: Readonly<ITourStep> = {};
