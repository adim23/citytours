import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import TourSchedule from './tour-schedule';
import TourScheduleDetail from './tour-schedule-detail';
import TourScheduleUpdate from './tour-schedule-update';
import TourScheduleDeleteDialog from './tour-schedule-delete-dialog';

const TourScheduleRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<TourSchedule />} />
    <Route path="new" element={<TourScheduleUpdate />} />
    <Route path=":id">
      <Route index element={<TourScheduleDetail />} />
      <Route path="edit" element={<TourScheduleUpdate />} />
      <Route path="delete" element={<TourScheduleDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TourScheduleRoutes;
