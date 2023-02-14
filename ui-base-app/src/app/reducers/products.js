const initialState = [];

export const ADD_PRODUCT = "ADD_PRODUCT"
export const DELETE_PRODUCT = "DELETE_PRODUCT"
export const SET_PRODUCTS = "SET_PRODUCTS"
export const UPDATE_PRODUCT = "UPDATE_PRODUCT"

export default (state = initialState, {type, payload}) => {
    switch (type) {
        case ADD_PRODUCT :
            return [
                ...state,
                {
                    id: payload.id,
                    price: payload.price,
                    name: payload.name,
                    category: payload.category
                }
            ]
        case DELETE_PRODUCT:
            return state.filter(product => product.id !== payload.id)
        case SET_PRODUCTS:
            return [...payload]
        case UPDATE_PRODUCT:
            return state.map(s => s.id === payload.id ? payload : s)
        default: return state;
    }
}