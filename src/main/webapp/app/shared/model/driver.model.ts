import dayjs from 'dayjs';
import { ITourSchedule } from 'app/shared/model/tour-schedule.model';

export interface IDriver {
  id?: number;
  firstName?: string;
  lastName?: string;
  hiredAt?: dayjs.Dayjs | null;
  age?: number | null;
  email?: string | null;
  mobile?: string | null;
  tourSchedules?: ITourSchedule[] | null;
}

export const defaultValue: Readonly<IDriver> = {};
