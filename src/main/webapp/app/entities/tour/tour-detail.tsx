import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './tour.reducer';

export const TourDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const tourEntity = useAppSelector(state => state.tour.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="tourDetailsHeading">
          <Translate contentKey="citytoursApp.tour.detail.title">Tour</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{tourEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="citytoursApp.tour.code">Code</Translate>
            </span>
          </dt>
          <dd>{tourEntity.code}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="citytoursApp.tour.title">Title</Translate>
            </span>
          </dt>
          <dd>{tourEntity.title}</dd>
          <dt>
            <span id="duration">
              <Translate contentKey="citytoursApp.tour.duration">Duration</Translate>
            </span>
          </dt>
          <dd>{tourEntity.duration}</dd>
          <dt>
            <span id="petFriendly">
              <Translate contentKey="citytoursApp.tour.petFriendly">Pet Friendly</Translate>
            </span>
          </dt>
          <dd>{tourEntity.petFriendly ? 'true' : 'false'}</dd>
          <dt>
            <span id="kidsAllowed">
              <Translate contentKey="citytoursApp.tour.kidsAllowed">Kids Allowed</Translate>
            </span>
          </dt>
          <dd>{tourEntity.kidsAllowed ? 'true' : 'false'}</dd>
          <dt>
            <span id="availableFromDate">
              <Translate contentKey="citytoursApp.tour.availableFromDate">Available From Date</Translate>
            </span>
          </dt>
          <dd>
            {tourEntity.availableFromDate ? (
              <TextFormat value={tourEntity.availableFromDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="availableToDate">
              <Translate contentKey="citytoursApp.tour.availableToDate">Available To Date</Translate>
            </span>
          </dt>
          <dd>
            {tourEntity.availableToDate ? (
              <TextFormat value={tourEntity.availableToDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="enabled">
              <Translate contentKey="citytoursApp.tour.enabled">Enabled</Translate>
            </span>
          </dt>
          <dd>{tourEntity.enabled ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="citytoursApp.tour.city">City</Translate>
          </dt>
          <dd>{tourEntity.city ? tourEntity.city.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/tour" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/tour/${tourEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TourDetail;
