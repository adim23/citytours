import dayjs from 'dayjs';
import { ITourSchedule } from 'app/shared/model/tour-schedule.model';
import { IPassenger } from 'app/shared/model/passenger.model';

export interface IBooking {
  id?: number;
  bookDatetime?: dayjs.Dayjs;
  noPersons?: number | null;
  noKids?: number | null;
  noPets?: number | null;
  cost?: number | null;
  paymentType?: string | null;
  valid?: boolean | null;
  cancelledAt?: dayjs.Dayjs | null;
  remoteData?: string | null;
  remoteId?: string | null;
  schedule?: ITourSchedule | null;
  passenger?: IPassenger | null;
}

export const defaultValue: Readonly<IBooking> = {
  valid: false,
};
