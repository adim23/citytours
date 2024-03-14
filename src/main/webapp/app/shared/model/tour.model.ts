import dayjs from 'dayjs';
import { ITourSchedule } from 'app/shared/model/tour-schedule.model';
import { ITourStep } from 'app/shared/model/tour-step.model';
import { ICity } from 'app/shared/model/city.model';

export interface ITour {
  id?: number;
  code?: string | null;
  title?: string;
  duration?: number;
  petFriendly?: boolean;
  kidsAllowed?: boolean;
  availableFromDate?: dayjs.Dayjs | null;
  availableToDate?: dayjs.Dayjs | null;
  enabled?: boolean;
  tourSchedules?: ITourSchedule[] | null;
  steps?: ITourStep[] | null;
  city?: ICity | null;
}

export const defaultValue: Readonly<ITour> = {
  petFriendly: false,
  kidsAllowed: false,
  enabled: false,
};
