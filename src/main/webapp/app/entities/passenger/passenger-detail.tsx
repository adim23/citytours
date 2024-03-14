import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './passenger.reducer';

export const PassengerDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const passengerEntity = useAppSelector(state => state.passenger.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="passengerDetailsHeading">
          <Translate contentKey="citytoursApp.passenger.detail.title">Passenger</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{passengerEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="citytoursApp.passenger.name">Name</Translate>
            </span>
          </dt>
          <dd>{passengerEntity.name}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="citytoursApp.passenger.email">Email</Translate>
            </span>
          </dt>
          <dd>{passengerEntity.email}</dd>
          <dt>
            <span id="mobile">
              <Translate contentKey="citytoursApp.passenger.mobile">Mobile</Translate>
            </span>
          </dt>
          <dd>{passengerEntity.mobile}</dd>
          <dt>
            <span id="age">
              <Translate contentKey="citytoursApp.passenger.age">Age</Translate>
            </span>
          </dt>
          <dd>{passengerEntity.age}</dd>
          <dt>
            <span id="gender">
              <Translate contentKey="citytoursApp.passenger.gender">Gender</Translate>
            </span>
          </dt>
          <dd>{passengerEntity.gender}</dd>
          <dt>
            <span id="nationality">
              <Translate contentKey="citytoursApp.passenger.nationality">Nationality</Translate>
            </span>
          </dt>
          <dd>{passengerEntity.nationality}</dd>
        </dl>
        <Button tag={Link} to="/passenger" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/passenger/${passengerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PassengerDetail;
