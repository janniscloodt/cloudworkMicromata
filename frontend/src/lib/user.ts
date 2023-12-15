import {writable} from "svelte/store";

export const auth = writable<Auth | null>(null);

type Auth = {
    username: string,
    email: string
}