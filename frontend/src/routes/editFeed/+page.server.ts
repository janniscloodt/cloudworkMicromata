import type {PageServerLoad} from "./$types";
import {redirect} from "@sveltejs/kit";
export const load: PageServerLoad = async ({locals}) => {
    if(!locals.user){
        console.log("Access denied!")
        throw redirect(303, "/");
    }
}

