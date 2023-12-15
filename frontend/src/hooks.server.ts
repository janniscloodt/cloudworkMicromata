import type {Handle} from "@sveltejs/kit"

export const handle: Handle = async ({event, resolve}) => {

    let cookies = event.cookies;
    let authToken = cookies.get("authToken");
    if (authToken) {
        let {username, email} = parseJwt(authToken);

        event.locals.user = {
            username, email
        }
        event.locals.token = authToken;
    }

    const response = await resolve(event);

    return response;
}

function parseJwt (token: string) {
    return JSON.parse(Buffer.from(token.split('.')[1], 'base64').toString());
}