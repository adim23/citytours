import { IBooking } from 'app/shared/model/booking.model';

export interface IPassenger {
  id?: number;
  name?: string | null;
  email?: string | null;
  mobile?: string | null;
  age?: number | null;
  gender?: string | null;
  nationality?: string | null;
  bookings?: IBooking[] | null;
}

export const defaultValue: Readonly<IPassenger> = {};
