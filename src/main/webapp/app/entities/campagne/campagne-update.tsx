import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './campagne.reducer';
import { ICampagne } from 'app/shared/model/campagne.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICampagneUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ICampagneUpdateState {
  isNew: boolean;
}

export class CampagneUpdate extends React.Component<ICampagneUpdateProps, ICampagneUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentDidMount() {
    if (!this.state.isNew) {
      this.props.getEntity(this.props.match.params.id);
    }
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { campagneEntity } = this.props;
      const entity = {
        ...campagneEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
      this.handleClose();
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/campagne');
  };

  render() {
    const { campagneEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="biodifullPocApp.campagne.home.createOrEditLabel">Create or edit a Campagne</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : campagneEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="campagne-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="campagneNameLabel" for="campagneName">
                    Campagne Name
                  </Label>
                  <AvField id="campagne-campagneName" type="text" name="campagneName" />
                </AvGroup>
                <AvGroup>
                  <Label id="campagneDescriptionLabel" for="campagneDescription">
                    Campagne Description
                  </Label>
                  <AvField id="campagne-campagneDescription" type="text" name="campagneDescription" />
                </AvGroup>
                <AvGroup>
                  <Label id="formURLLabel" for="formURL">
                    Form URL
                  </Label>
                  <AvField id="campagne-formURL" type="text" name="formURL" />
                </AvGroup>
                <AvGroup>
                  <Label id="challengersLocationLabel" for="challengersLocation">
                    Challengers Location
                  </Label>
                  <AvField id="campagne-challengersLocation" type="text" name="challengersLocation" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/campagne" replace color="info">
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
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  campagneEntity: storeState.campagne.entity,
  loading: storeState.campagne.loading,
  updating: storeState.campagne.updating
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(CampagneUpdate);
