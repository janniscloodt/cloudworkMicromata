import {error, fail, redirect} from "@sveltejs/kit";
import type { Cookies } from "@sveltejs/kit";
export async function setCookie(response: Response, cookies: Cookies) {

    if (!response.ok) {
        throw error(400);
    }

    const responseData = await response.json();
    const authToken = responseData.token;

// Setze das Cookie mit einer Gültigkeitsdauer von 30 Tagen
    const expirationDate = new Date();
    expirationDate.setDate(expirationDate.getDate() + 30);

    cookies.set('authToken', authToken, {expires: expirationDate});

    throw redirect(302, '/');
}