import axios from 'axios';
import {
  parseHeaderForLinks,
  loadMoreDataWhenScrolled,
  ICrudGetAction,
  ICrudGetAllAction,
  ICrudPutAction,
  ICrudDeleteAction
} from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICampagne, defaultValue } from 'app/shared/model/campagne.model';

export const ACTION_TYPES = {
  FETCH_CAMPAGNE_LIST: 'campagne/FETCH_CAMPAGNE_LIST',
  FETCH_CAMPAGNE: 'campagne/FETCH_CAMPAGNE',
  CREATE_CAMPAGNE: 'campagne/CREATE_CAMPAGNE',
  UPDATE_CAMPAGNE: 'campagne/UPDATE_CAMPAGNE',
  DELETE_CAMPAGNE: 'campagne/DELETE_CAMPAGNE',
  RESET: 'campagne/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICampagne>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type CampagneState = Readonly<typeof initialState>;

// Reducer

export default (state: CampagneState = initialState, action): CampagneState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CAMPAGNE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CAMPAGNE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_CAMPAGNE):
    case REQUEST(ACTION_TYPES.UPDATE_CAMPAGNE):
    case REQUEST(ACTION_TYPES.DELETE_CAMPAGNE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_CAMPAGNE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CAMPAGNE):
    case FAILURE(ACTION_TYPES.CREATE_CAMPAGNE):
    case FAILURE(ACTION_TYPES.UPDATE_CAMPAGNE):
    case FAILURE(ACTION_TYPES.DELETE_CAMPAGNE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_CAMPAGNE_LIST):
      const links = parseHeaderForLinks(action.payload.headers.link);
      return {
        ...state,
        links,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links)
      };
    case SUCCESS(ACTION_TYPES.FETCH_CAMPAGNE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_CAMPAGNE):
    case SUCCESS(ACTION_TYPES.UPDATE_CAMPAGNE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_CAMPAGNE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/campagnes';

// Actions

export const getEntities: ICrudGetAllAction<ICampagne> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_CAMPAGNE_LIST,
    payload: axios.get<ICampagne>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<ICampagne> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CAMPAGNE,
    payload: axios.get<ICampagne>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ICampagne> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CAMPAGNE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<ICampagne> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CAMPAGNE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICampagne> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CAMPAGNE,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
