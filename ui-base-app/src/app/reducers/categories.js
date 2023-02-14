const initialState = [];

export const SET_CATEGORIES = "SET_CATEGORIES"

export default (state = initialState, {type, payload}) => {
    switch (type) {
        case SET_CATEGORIES:
            return [...payload]
        default: return state;
    }
}