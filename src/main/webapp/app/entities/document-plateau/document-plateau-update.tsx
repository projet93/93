import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, setFileData, openFile, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IPlateau } from 'app/shared/model/plateau.model';
import { getEntities as getPlateaus } from 'app/entities/plateau/plateau.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './document-plateau.reducer';
import { IDocumentPlateau } from 'app/shared/model/document-plateau.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IDocumentPlateauUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const DocumentPlateauUpdate = (props: IDocumentPlateauUpdateProps) => {
  const [plateauId, setPlateauId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { documentPlateauEntity, plateaus, loading, updating } = props;

  const { programme, programmeContentType } = documentPlateauEntity;

  const handleClose = () => {
    props.history.push('/document-plateau');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getPlateaus();
  }, []);

  const onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => props.setBlob(name, data, contentType), isAnImage);
  };

  const clearBlob = name => () => {
    props.setBlob(name, undefined, undefined);
  };

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...documentPlateauEntity,
        ...values
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="App.documentPlateau.home.createOrEditLabel">Create or edit a DocumentPlateau</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : documentPlateauEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="document-plateau-id">ID</Label>
                  <AvInput id="document-plateau-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <AvGroup>
                  <Label id="programmeLabel" for="programme">
                    Programme
                  </Label>
                  <br />
                  {programme ? (
                    <div>
                      <a onClick={openFile(programmeContentType, programme)}>Open</a>
                      <br />
                      <Row>
                        <Col md="11">
                          <span>
                            {programmeContentType}, {byteSize(programme)}
                          </span>
                        </Col>
                        <Col md="1">
                          <Button color="danger" onClick={clearBlob('programme')}>
                            <FontAwesomeIcon icon="times-circle" />
                          </Button>
                        </Col>
                      </Row>
                    </div>
                  ) : null}
                  <input id="file_programme" type="file" onChange={onBlobChange(false, 'programme')} />
                  <AvInput type="hidden" name="programme" value={programme} />
                </AvGroup>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/document-plateau" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  plateaus: storeState.plateau.entities,
  documentPlateauEntity: storeState.documentPlateau.entity,
  loading: storeState.documentPlateau.loading,
  updating: storeState.documentPlateau.updating,
  updateSuccess: storeState.documentPlateau.updateSuccess
});

const mapDispatchToProps = {
  getPlateaus,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(DocumentPlateauUpdate);
