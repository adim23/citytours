import city from 'app/entities/city/city.reducer';
import place from 'app/entities/place/place.reducer';
import tour from 'app/entities/tour/tour.reducer';
import tourStep from 'app/entities/tour-step/tour-step.reducer';
import tourSchedule from 'app/entities/tour-schedule/tour-schedule.reducer';
import driver from 'app/entities/driver/driver.reducer';
import vehicle from 'app/entities/vehicle/vehicle.reducer';
import booking from 'app/entities/booking/booking.reducer';
import passenger from 'app/entities/passenger/passenger.reducer';
import imageFile from 'app/entities/image-file/image-file.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  city,
  place,
  tour,
  tourStep,
  tourSchedule,
  driver,
  vehicle,
  booking,
  passenger,
  imageFile,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
