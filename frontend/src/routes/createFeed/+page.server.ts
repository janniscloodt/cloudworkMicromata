import {setCookie} from "$lib/setCookie";
import type {Actions} from "@sveltejs/kit";
import {env} from "$env/dynamic/public";
import {error, redirect} from "@sveltejs/kit";

export const actions:Actions = {
    default: async ({request, fetch, locals}) => {

        const token = locals.token;

        const data = await request.formData();
        const title = data.get('title');
        const shortDescription = data.get('shortDescription');
        const content = data.get('content');


        const response = await fetch(env.PUBLIC_BACKEND_URL + 'v1/feed/new', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify({ title }),
        });

        if (!response.ok) {
            return await response.json();
        }

        throw redirect(303, '/blogs?created');

    }
}