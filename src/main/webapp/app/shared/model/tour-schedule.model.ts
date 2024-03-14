import dayjs from 'dayjs';
import { IBooking } from 'app/shared/model/booking.model';
import { ITour } from 'app/shared/model/tour.model';
import { IVehicle } from 'app/shared/model/vehicle.model';
import { IDriver } from 'app/shared/model/driver.model';

export interface ITourSchedule {
  id?: number;
  code?: string;
  startDatetime?: dayjs.Dayjs;
  noPassengers?: number | null;
  noKids?: number | null;
  noPets?: number | null;
  startPlace?: string | null;
  endPlace?: string | null;
  bookings?: IBooking[] | null;
  tour?: ITour | null;
  vehicle?: IVehicle | null;
  driver?: IDriver | null;
}

export const defaultValue: Readonly<ITourSchedule> = {};
