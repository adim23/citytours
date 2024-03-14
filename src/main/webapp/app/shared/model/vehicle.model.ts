import { ITourSchedule } from 'app/shared/model/tour-schedule.model';

export interface IVehicle {
  id?: number;
  plate?: string;
  type?: string;
  capacity?: number;
  color?: string | null;
  tourSchedules?: ITourSchedule[] | null;
}

export const defaultValue: Readonly<IVehicle> = {};
