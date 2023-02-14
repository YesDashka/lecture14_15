import configExample from "../../config/configExample";
import {ADD_PRODUCT, DELETE_PRODUCT, SET_PRODUCTS, UPDATE_PRODUCT} from "../reducers/products";

export const fetchProducts = () => (dispatch) => {
    return fetch(`${configExample.BASE_URL}/api/products`)
        .then(s => s.json())
        .then(payload => {
            return dispatch({type: SET_PRODUCTS, payload: payload})
        })
};

export const dropProduct = (product) => (dispatch) => {
    fetch(`${configExample.BASE_URL}/api/products/${product.id}`, {method: "DELETE"})
        .then(() => dispatch({type: DELETE_PRODUCT, payload: product}))
        .catch(console.log)
}

export const addProduct = (product) => (dispatch) => {
    fetch(`${configExample.BASE_URL}/api/products`, {
        method: "POST",
        body: JSON.stringify({price: product.price, name: product.name, categoryId: product.category.id}),
        headers: new Headers({'content-type': 'application/json'})
    })
        .then(s => s.json())
        .then(s => dispatch({type: ADD_PRODUCT, payload: {...s, category: {id: product.category.id, name: product.category.name}}}))
        .catch(console.log)
}

export const updateProduct = (product) => (dispatch) => {
    fetch(`${configExample.BASE_URL}/api/products/${product.id}` , {
        method: "PUT",
        body: JSON.stringify({price: product.price, name: product.name, categoryId: product.category.id}),
        headers: new Headers({'content-type': 'application/json'})
    })
        .then(s => s.json())
        .then(s => dispatch({type: UPDATE_PRODUCT, payload: {...s, category: {id: product.category.id, name: product.category.name}}}))
        .catch(console.log)
}