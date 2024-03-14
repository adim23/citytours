import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { byteSize, Translate, TextFormat, getPaginationState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './booking.reducer';

export const Booking = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );

  const bookingList = useAppSelector(state => state.booking.entities);
  const loading = useAppSelector(state => state.booking.loading);
  const totalItems = useAppSelector(state => state.booking.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(pageLocation.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [pageLocation.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = paginationState.sort;
    const order = paginationState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="booking-heading" data-cy="BookingHeading">
        <Translate contentKey="citytoursApp.booking.home.title">Bookings</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="citytoursApp.booking.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/booking/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="citytoursApp.booking.home.createLabel">Create new Booking</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {bookingList && bookingList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="citytoursApp.booking.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('bookDatetime')}>
                  <Translate contentKey="citytoursApp.booking.bookDatetime">Book Datetime</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('bookDatetime')} />
                </th>
                <th className="hand" onClick={sort('noPersons')}>
                  <Translate contentKey="citytoursApp.booking.noPersons">No Persons</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('noPersons')} />
                </th>
                <th className="hand" onClick={sort('noKids')}>
                  <Translate contentKey="citytoursApp.booking.noKids">No Kids</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('noKids')} />
                </th>
                <th className="hand" onClick={sort('noPets')}>
                  <Translate contentKey="citytoursApp.booking.noPets">No Pets</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('noPets')} />
                </th>
                <th className="hand" onClick={sort('cost')}>
                  <Translate contentKey="citytoursApp.booking.cost">Cost</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('cost')} />
                </th>
                <th className="hand" onClick={sort('paymentType')}>
                  <Translate contentKey="citytoursApp.booking.paymentType">Payment Type</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('paymentType')} />
                </th>
                <th className="hand" onClick={sort('valid')}>
                  <Translate contentKey="citytoursApp.booking.valid">Valid</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('valid')} />
                </th>
                <th className="hand" onClick={sort('cancelledAt')}>
                  <Translate contentKey="citytoursApp.booking.cancelledAt">Cancelled At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('cancelledAt')} />
                </th>
                <th className="hand" onClick={sort('remoteData')}>
                  <Translate contentKey="citytoursApp.booking.remoteData">Remote Data</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('remoteData')} />
                </th>
                <th className="hand" onClick={sort('remoteId')}>
                  <Translate contentKey="citytoursApp.booking.remoteId">Remote Id</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('remoteId')} />
                </th>
                <th>
                  <Translate contentKey="citytoursApp.booking.schedule">Schedule</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="citytoursApp.booking.passenger">Passenger</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {bookingList.map((booking, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/booking/${booking.id}`} color="link" size="sm">
                      {booking.id}
                    </Button>
                  </td>
                  <td>{booking.bookDatetime ? <TextFormat type="date" value={booking.bookDatetime} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{booking.noPersons}</td>
                  <td>{booking.noKids}</td>
                  <td>{booking.noPets}</td>
                  <td>{booking.cost}</td>
                  <td>{booking.paymentType}</td>
                  <td>{booking.valid ? 'true' : 'false'}</td>
                  <td>{booking.cancelledAt ? <TextFormat type="date" value={booking.cancelledAt} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{booking.remoteData}</td>
                  <td>{booking.remoteId}</td>
                  <td>{booking.schedule ? <Link to={`/tour-schedule/${booking.schedule.id}`}>{booking.schedule.id}</Link> : ''}</td>
                  <td>{booking.passenger ? <Link to={`/passenger/${booking.passenger.id}`}>{booking.passenger.name}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/booking/${booking.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/booking/${booking.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        onClick={() =>
                          (window.location.href = `/booking/${booking.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`)
                        }
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="citytoursApp.booking.home.notFound">No Bookings found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={bookingList && bookingList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default Booking;
