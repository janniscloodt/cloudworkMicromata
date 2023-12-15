import {error, fail, redirect} from "@sveltejs/kit";
import type { Cookies } from "@sveltejs/kit";
export async function setCookie(response: Response, cookies: Cookies) {

    const responseData = await response.json();
    const authToken = responseData.accessToken;

// Setze das Cookie mit einer Gültigkeitsdauer von 30 Tagen
    const expirationDate = new Date();
    expirationDate.setDate(expirationDate.getDate() + 30);

    cookies.set('authToken', authToken, {expires: expirationDate, httpOnly: true});

    throw redirect(302, '/');
}