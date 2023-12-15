import type {Actions} from "./$types";
import {env} from "$env/dynamic/public";
import {redirect} from "@sveltejs/kit";
import {setCookie} from "$lib/setCookie";

export const actions:Actions = {
    default: async ({request, fetch, cookies}) => {

        const data = await request.formData();
        const username = data.get('username');
        const email = data.get('email')
        const password = data.get('password');
        const passwordConfirm = data.get('passwordConfirm');

        const response = await fetch(env.PUBLIC_BACKEND_URL + 'v1/auth/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ username, email, password, passwordConfirm }),
        });

        await setCookie(response, cookies);
    }
}