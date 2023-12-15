import type {PageServerLoad} from "./$types"
import {redirect} from "@sveltejs/kit";
import type {Actions} from "@sveltejs/kit";

export const actions: Actions = {
    default: ({cookies}) => {
        cookies.delete("authToken");
        throw redirect(302, "/");
    }
}