import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { openFile, byteSize, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './logo-club.reducer';
import { ILogoClub } from 'app/shared/model/logo-club.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ILogoClubProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const LogoClub = (props: ILogoClubProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { logoClubList, match, loading } = props;
  return (
    <div>
      <h2 id="logo-club-heading">
        Logo Clubs
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp; Create new Logo Club
        </Link>
      </h2>
      <div className="table-responsive">
        {logoClubList && logoClubList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Logo</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {logoClubList.map((logoClub, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${logoClub.id}`} color="link" size="sm">
                      {logoClub.id}
                    </Button>
                  </td>
                  <td>
                    {logoClub.logo ? (
                      <div>
                        <a onClick={openFile(logoClub.logoContentType, logoClub.logo)}>Open &nbsp;</a>
                        <span>
                          {logoClub.logoContentType}, {byteSize(logoClub.logo)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${logoClub.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${logoClub.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${logoClub.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Logo Clubs found</div>
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ logoClub }: IRootState) => ({
  logoClubList: logoClub.entities,
  loading: logoClub.loading
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(LogoClub);
