import { combineReducers } from 'redux';

import reducer from './reducer';
import products from "../../../app/reducers/products";

export default combineReducers({
  reducer,
  products
});