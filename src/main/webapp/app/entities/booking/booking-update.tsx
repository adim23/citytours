import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITourSchedule } from 'app/shared/model/tour-schedule.model';
import { getEntities as getTourSchedules } from 'app/entities/tour-schedule/tour-schedule.reducer';
import { IPassenger } from 'app/shared/model/passenger.model';
import { getEntities as getPassengers } from 'app/entities/passenger/passenger.reducer';
import { IBooking } from 'app/shared/model/booking.model';
import { getEntity, updateEntity, createEntity, reset } from './booking.reducer';

export const BookingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const tourSchedules = useAppSelector(state => state.tourSchedule.entities);
  const passengers = useAppSelector(state => state.passenger.entities);
  const bookingEntity = useAppSelector(state => state.booking.entity);
  const loading = useAppSelector(state => state.booking.loading);
  const updating = useAppSelector(state => state.booking.updating);
  const updateSuccess = useAppSelector(state => state.booking.updateSuccess);

  const handleClose = () => {
    navigate('/booking' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getTourSchedules({}));
    dispatch(getPassengers({}));
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
    values.bookDatetime = convertDateTimeToServer(values.bookDatetime);
    if (values.noPersons !== undefined && typeof values.noPersons !== 'number') {
      values.noPersons = Number(values.noPersons);
    }
    if (values.noKids !== undefined && typeof values.noKids !== 'number') {
      values.noKids = Number(values.noKids);
    }
    if (values.noPets !== undefined && typeof values.noPets !== 'number') {
      values.noPets = Number(values.noPets);
    }
    if (values.cost !== undefined && typeof values.cost !== 'number') {
      values.cost = Number(values.cost);
    }
    values.cancelledAt = convertDateTimeToServer(values.cancelledAt);

    const entity = {
      ...bookingEntity,
      ...values,
      schedule: tourSchedules.find(it => it.id.toString() === values.schedule.toString()),
      passenger: passengers.find(it => it.id.toString() === values.passenger.toString()),
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
          bookDatetime: displayDefaultDateTime(),
          cancelledAt: displayDefaultDateTime(),
        }
      : {
          ...bookingEntity,
          bookDatetime: convertDateTimeFromServer(bookingEntity.bookDatetime),
          cancelledAt: convertDateTimeFromServer(bookingEntity.cancelledAt),
          schedule: bookingEntity?.schedule?.id,
          passenger: bookingEntity?.passenger?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="citytoursApp.booking.home.createOrEditLabel" data-cy="BookingCreateUpdateHeading">
            <Translate contentKey="citytoursApp.booking.home.createOrEditLabel">Create or edit a Booking</Translate>
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
                  id="booking-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('citytoursApp.booking.bookDatetime')}
                id="booking-bookDatetime"
                name="bookDatetime"
                data-cy="bookDatetime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('citytoursApp.booking.noPersons')}
                id="booking-noPersons"
                name="noPersons"
                data-cy="noPersons"
                type="text"
              />
              <ValidatedField
                label={translate('citytoursApp.booking.noKids')}
                id="booking-noKids"
                name="noKids"
                data-cy="noKids"
                type="text"
              />
              <ValidatedField
                label={translate('citytoursApp.booking.noPets')}
                id="booking-noPets"
                name="noPets"
                data-cy="noPets"
                type="text"
              />
              <ValidatedField label={translate('citytoursApp.booking.cost')} id="booking-cost" name="cost" data-cy="cost" type="text" />
              <ValidatedField
                label={translate('citytoursApp.booking.paymentType')}
                id="booking-paymentType"
                name="paymentType"
                data-cy="paymentType"
                type="text"
              />
              <ValidatedField
                label={translate('citytoursApp.booking.valid')}
                id="booking-valid"
                name="valid"
                data-cy="valid"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('citytoursApp.booking.cancelledAt')}
                id="booking-cancelledAt"
                name="cancelledAt"
                data-cy="cancelledAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('citytoursApp.booking.remoteData')}
                id="booking-remoteData"
                name="remoteData"
                data-cy="remoteData"
                type="textarea"
              />
              <ValidatedField
                label={translate('citytoursApp.booking.remoteId')}
                id="booking-remoteId"
                name="remoteId"
                data-cy="remoteId"
                type="text"
              />
              <ValidatedField
                id="booking-schedule"
                name="schedule"
                data-cy="schedule"
                label={translate('citytoursApp.booking.schedule')}
                type="select"
              >
                <option value="" key="0" />
                {tourSchedules
                  ? tourSchedules.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.code}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="booking-passenger"
                name="passenger"
                data-cy="passenger"
                label={translate('citytoursApp.booking.passenger')}
                type="select"
              >
                <option value="" key="0" />
                {passengers
                  ? passengers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/booking" replace color="info">
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

export default BookingUpdate;
