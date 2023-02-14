import React from 'react';
import { applyMiddleware, createStore, combineReducers } from 'redux';
import { Provider } from 'react-redux';
import thunkMiddleware from 'redux-thunk';

import App from './containers/App.jsx';
import userReducer from './reducers/user';
import productsReducer from './reducers/products';
import categoriesReducer from './reducers/categories';

const rootReducer = combineReducers({
  user: userReducer,
  products: productsReducer,
  categories: categoriesReducer
});

const store = createStore(
  rootReducer,
  applyMiddleware(thunkMiddleware),
);

export default () => (
  <Provider store={store} >
    <App />
  </Provider>
)
