import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './logo-club.reducer';
import { ILogoClub } from 'app/shared/model/logo-club.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ILogoClubDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const LogoClubDetail = (props: ILogoClubDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { logoClubEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          LogoClub [<b>{logoClubEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="logo">Logo</span>
          </dt>
          <dd>
            {logoClubEntity.logo ? (
              <div>
                <a onClick={openFile(logoClubEntity.logoContentType, logoClubEntity.logo)}>Open&nbsp;</a>
                <span>
                  {logoClubEntity.logoContentType}, {byteSize(logoClubEntity.logo)}
                </span>
              </div>
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/logo-club" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/logo-club/${logoClubEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ logoClub }: IRootState) => ({
  logoClubEntity: logoClub.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(LogoClubDetail);
