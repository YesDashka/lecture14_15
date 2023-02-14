import {getToken,} from 'token';

const getHeaders = () => ({
    Accept: 'application/json',
    Authorization: `Bearer ${getToken()}`,
    'Content-Type': 'application/json',
});

const fetchGet = ({params = {}, url}) => {
    url = new URL(url);
    url.search = new URLSearchParams(params).toString();
    return fetch(
        url,
        {
            headers: getHeaders(),
            method: 'GET',
        }
    );
};

const fetchPost = ({body, params = {}, url}) => {
    const newUrl = new URL(url);
    newUrl.search = new URLSearchParams(params).toString();

    return fetch(
        newUrl,
        {
            body: JSON.stringify(body),
            headers: getHeaders(),
            method: 'POST',
        }
    );
};

export const getJson = ({
                            params,
                            url,
                        }) => {
    return fetchGet({
        params,
        url,
    }).then((response) => {
        if (response.ok) {
            return response.json();
        }
        throw response;
    });
};

export const postJson = ({
                             body,
                             params,
                             url,
                         }) => {
    return fetchPost({
        body,
        params,
        url,
    }).then((response) => {
        if (response.ok) {
            return response.json();
        }
        throw response;
    });
};
