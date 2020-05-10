import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ILogoClub, defaultValue } from 'app/shared/model/logo-club.model';

export const ACTION_TYPES = {
  FETCH_LOGOCLUB_LIST: 'logoClub/FETCH_LOGOCLUB_LIST',
  FETCH_LOGOCLUB: 'logoClub/FETCH_LOGOCLUB',
  CREATE_LOGOCLUB: 'logoClub/CREATE_LOGOCLUB',
  UPDATE_LOGOCLUB: 'logoClub/UPDATE_LOGOCLUB',
  DELETE_LOGOCLUB: 'logoClub/DELETE_LOGOCLUB',
  SET_BLOB: 'logoClub/SET_BLOB',
  RESET: 'logoClub/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ILogoClub>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type LogoClubState = Readonly<typeof initialState>;

// Reducer

export default (state: LogoClubState = initialState, action): LogoClubState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_LOGOCLUB_LIST):
    case REQUEST(ACTION_TYPES.FETCH_LOGOCLUB):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_LOGOCLUB):
    case REQUEST(ACTION_TYPES.UPDATE_LOGOCLUB):
    case REQUEST(ACTION_TYPES.DELETE_LOGOCLUB):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_LOGOCLUB_LIST):
    case FAILURE(ACTION_TYPES.FETCH_LOGOCLUB):
    case FAILURE(ACTION_TYPES.CREATE_LOGOCLUB):
    case FAILURE(ACTION_TYPES.UPDATE_LOGOCLUB):
    case FAILURE(ACTION_TYPES.DELETE_LOGOCLUB):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_LOGOCLUB_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_LOGOCLUB):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_LOGOCLUB):
    case SUCCESS(ACTION_TYPES.UPDATE_LOGOCLUB):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_LOGOCLUB):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.SET_BLOB: {
      const { name, data, contentType } = action.payload;
      return {
        ...state,
        entity: {
          ...state.entity,
          [name]: data,
          [name + 'ContentType']: contentType
        }
      };
    }
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/logo-clubs';

// Actions

export const getEntities: ICrudGetAllAction<ILogoClub> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_LOGOCLUB_LIST,
  payload: axios.get<ILogoClub>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<ILogoClub> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_LOGOCLUB,
    payload: axios.get<ILogoClub>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ILogoClub> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_LOGOCLUB,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ILogoClub> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_LOGOCLUB,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ILogoClub> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_LOGOCLUB,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const setBlob = (name, data, contentType?) => ({
  type: ACTION_TYPES.SET_BLOB,
  payload: {
    name,
    data,
    contentType
  }
});

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
