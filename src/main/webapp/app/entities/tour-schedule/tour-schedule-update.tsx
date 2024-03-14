import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITour } from 'app/shared/model/tour.model';
import { getEntities as getTours } from 'app/entities/tour/tour.reducer';
import { IVehicle } from 'app/shared/model/vehicle.model';
import { getEntities as getVehicles } from 'app/entities/vehicle/vehicle.reducer';
import { IDriver } from 'app/shared/model/driver.model';
import { getEntities as getDrivers } from 'app/entities/driver/driver.reducer';
import { ITourSchedule } from 'app/shared/model/tour-schedule.model';
import { getEntity, updateEntity, createEntity, reset } from './tour-schedule.reducer';

export const TourScheduleUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const tours = useAppSelector(state => state.tour.entities);
  const vehicles = useAppSelector(state => state.vehicle.entities);
  const drivers = useAppSelector(state => state.driver.entities);
  const tourScheduleEntity = useAppSelector(state => state.tourSchedule.entity);
  const loading = useAppSelector(state => state.tourSchedule.loading);
  const updating = useAppSelector(state => state.tourSchedule.updating);
  const updateSuccess = useAppSelector(state => state.tourSchedule.updateSuccess);

  const handleClose = () => {
    navigate('/tour-schedule' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getTours({}));
    dispatch(getVehicles({}));
    dispatch(getDrivers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }
    values.startDatetime = convertDateTimeToServer(values.startDatetime);
    if (values.noPassengers !== undefined && typeof values.noPassengers !== 'number') {
      values.noPassengers = Number(values.noPassengers);
    }
    if (values.noKids !== undefined && typeof values.noKids !== 'number') {
      values.noKids = Number(values.noKids);
    }
    if (values.noPets !== undefined && typeof values.noPets !== 'number') {
      values.noPets = Number(values.noPets);
    }

    const entity = {
      ...tourScheduleEntity,
      ...values,
      tour: tours.find(it => it.id.toString() === values.tour.toString()),
      vehicle: vehicles.find(it => it.id.toString() === values.vehicle.toString()),
      driver: drivers.find(it => it.id.toString() === values.driver.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          startDatetime: displayDefaultDateTime(),
        }
      : {
          ...tourScheduleEntity,
          startDatetime: convertDateTimeFromServer(tourScheduleEntity.startDatetime),
          tour: tourScheduleEntity?.tour?.id,
          vehicle: tourScheduleEntity?.vehicle?.id,
          driver: tourScheduleEntity?.driver?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="citytoursApp.tourSchedule.home.createOrEditLabel" data-cy="TourScheduleCreateUpdateHeading">
            <Translate contentKey="citytoursApp.tourSchedule.home.createOrEditLabel">Create or edit a TourSchedule</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="tour-schedule-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('citytoursApp.tourSchedule.code')}
                id="tour-schedule-code"
                name="code"
                data-cy="code"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('citytoursApp.tourSchedule.startDatetime')}
                id="tour-schedule-startDatetime"
                name="startDatetime"
                data-cy="startDatetime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('citytoursApp.tourSchedule.noPassengers')}
                id="tour-schedule-noPassengers"
                name="noPassengers"
                data-cy="noPassengers"
                type="text"
              />
              <ValidatedField
                label={translate('citytoursApp.tourSchedule.noKids')}
                id="tour-schedule-noKids"
                name="noKids"
                data-cy="noKids"
                type="text"
              />
              <ValidatedField
                label={translate('citytoursApp.tourSchedule.noPets')}
                id="tour-schedule-noPets"
                name="noPets"
                data-cy="noPets"
                type="text"
              />
              <ValidatedField
                label={translate('citytoursApp.tourSchedule.startPlace')}
                id="tour-schedule-startPlace"
                name="startPlace"
                data-cy="startPlace"
                type="text"
              />
              <ValidatedField
                label={translate('citytoursApp.tourSchedule.endPlace')}
                id="tour-schedule-endPlace"
                name="endPlace"
                data-cy="endPlace"
                type="text"
              />
              <ValidatedField
                id="tour-schedule-tour"
                name="tour"
                data-cy="tour"
                label={translate('citytoursApp.tourSchedule.tour')}
                type="select"
              >
                <option value="" key="0" />
                {tours
                  ? tours.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.title}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="tour-schedule-vehicle"
                name="vehicle"
                data-cy="vehicle"
                label={translate('citytoursApp.tourSchedule.vehicle')}
                type="select"
              >
                <option value="" key="0" />
                {vehicles
                  ? vehicles.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.plate}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="tour-schedule-driver"
                name="driver"
                data-cy="driver"
                label={translate('citytoursApp.tourSchedule.driver')}
                type="select"
              >
                <option value="" key="0" />
                {drivers
                  ? drivers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/tour-schedule" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default TourScheduleUpdate;
