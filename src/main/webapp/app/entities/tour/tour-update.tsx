import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICity } from 'app/shared/model/city.model';
import { getEntities as getCities } from 'app/entities/city/city.reducer';
import { ITour } from 'app/shared/model/tour.model';
import { getEntity, updateEntity, createEntity, reset } from './tour.reducer';

export const TourUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const cities = useAppSelector(state => state.city.entities);
  const tourEntity = useAppSelector(state => state.tour.entity);
  const loading = useAppSelector(state => state.tour.loading);
  const updating = useAppSelector(state => state.tour.updating);
  const updateSuccess = useAppSelector(state => state.tour.updateSuccess);

  const handleClose = () => {
    navigate('/tour' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getCities({}));
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
    if (values.duration !== undefined && typeof values.duration !== 'number') {
      values.duration = Number(values.duration);
    }

    const entity = {
      ...tourEntity,
      ...values,
      city: cities.find(it => it.id.toString() === values.city.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...tourEntity,
          city: tourEntity?.city?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="citytoursApp.tour.home.createOrEditLabel" data-cy="TourCreateUpdateHeading">
            <Translate contentKey="citytoursApp.tour.home.createOrEditLabel">Create or edit a Tour</Translate>
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
                  id="tour-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('citytoursApp.tour.code')} id="tour-code" name="code" data-cy="code" type="text" />
              <ValidatedField
                label={translate('citytoursApp.tour.title')}
                id="tour-title"
                name="title"
                data-cy="title"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('citytoursApp.tour.duration')}
                id="tour-duration"
                name="duration"
                data-cy="duration"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('citytoursApp.tour.petFriendly')}
                id="tour-petFriendly"
                name="petFriendly"
                data-cy="petFriendly"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('citytoursApp.tour.kidsAllowed')}
                id="tour-kidsAllowed"
                name="kidsAllowed"
                data-cy="kidsAllowed"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('citytoursApp.tour.availableFromDate')}
                id="tour-availableFromDate"
                name="availableFromDate"
                data-cy="availableFromDate"
                type="date"
              />
              <ValidatedField
                label={translate('citytoursApp.tour.availableToDate')}
                id="tour-availableToDate"
                name="availableToDate"
                data-cy="availableToDate"
                type="date"
              />
              <ValidatedField
                label={translate('citytoursApp.tour.enabled')}
                id="tour-enabled"
                name="enabled"
                data-cy="enabled"
                check
                type="checkbox"
              />
              <ValidatedField id="tour-city" name="city" data-cy="city" label={translate('citytoursApp.tour.city')} type="select">
                <option value="" key="0" />
                {cities
                  ? cities.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/tour" replace color="info">
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

export default TourUpdate;
