import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './campagne.reducer';
import { ICampagne } from 'app/shared/model/campagne.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICampagneDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class CampagneDetail extends React.Component<ICampagneDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { campagneEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Campagne [<b>{campagneEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="campagneName">Campagne Name</span>
            </dt>
            <dd>{campagneEntity.campagneName}</dd>
            <dt>
              <span id="campagneDescription">Campagne Description</span>
            </dt>
            <dd>{campagneEntity.campagneDescription}</dd>
            <dt>
              <span id="formURL">Form URL</span>
            </dt>
            <dd>{campagneEntity.formURL}</dd>
            <dt>
              <span id="challengersLocation">Challengers Location</span>
            </dt>
            <dd>{campagneEntity.challengersLocation}</dd>
          </dl>
          <Button tag={Link} to="/entity/campagne" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/campagne/${campagneEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ campagne }: IRootState) => ({
  campagneEntity: campagne.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(CampagneDetail);
