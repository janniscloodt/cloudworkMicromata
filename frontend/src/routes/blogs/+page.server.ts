import type {PageServerLoad} from "./$types";
import {env} from "$env/dynamic/public";

export const load: PageServerLoad = async ({fetch}) => {

    const response = await fetch(env.PUBLIC_BACKEND_URL + "v1/feed", {
        method: "GET"
    });

    return await response.json();

}