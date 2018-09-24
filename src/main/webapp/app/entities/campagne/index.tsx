import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Campagne from './campagne';
import CampagneDetail from './campagne-detail';
import CampagneUpdate from './campagne-update';
import CampagneDeleteDialog from './campagne-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CampagneUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CampagneUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CampagneDetail} />
      <ErrorBoundaryRoute path={match.url} component={Campagne} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={CampagneDeleteDialog} />
  </>
);

export default Routes;
