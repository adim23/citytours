import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './place.reducer';

export const PlaceDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const placeEntity = useAppSelector(state => state.place.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="placeDetailsHeading">
          <Translate contentKey="citytoursApp.place.detail.title">Place</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{placeEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="citytoursApp.place.code">Code</Translate>
            </span>
          </dt>
          <dd>{placeEntity.code}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="citytoursApp.place.name">Name</Translate>
            </span>
          </dt>
          <dd>{placeEntity.name}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="citytoursApp.place.description">Description</Translate>
            </span>
          </dt>
          <dd>{placeEntity.description}</dd>
          <dt>
            <span id="fullDescription">
              <Translate contentKey="citytoursApp.place.fullDescription">Full Description</Translate>
            </span>
          </dt>
          <dd>{placeEntity.fullDescription}</dd>
          <dt>
            <span id="longitude">
              <Translate contentKey="citytoursApp.place.longitude">Longitude</Translate>
            </span>
          </dt>
          <dd>{placeEntity.longitude}</dd>
          <dt>
            <span id="latitude">
              <Translate contentKey="citytoursApp.place.latitude">Latitude</Translate>
            </span>
          </dt>
          <dd>{placeEntity.latitude}</dd>
          <dt>
            <Translate contentKey="citytoursApp.place.city">City</Translate>
          </dt>
          <dd>{placeEntity.city ? placeEntity.city.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/place" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/place/${placeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PlaceDetail;
