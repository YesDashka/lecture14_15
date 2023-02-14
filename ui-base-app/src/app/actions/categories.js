import configExample from "../../config/configExample";
import {SET_CATEGORIES} from "../reducers/categories";

export const fetchCategories = () => (dispatch) => {
    return fetch(`${configExample.BASE_URL}/api/categories`)
        .then(s => s.json())
        .then(payload => {
            return dispatch({type: SET_CATEGORIES, payload: payload})
        })
};