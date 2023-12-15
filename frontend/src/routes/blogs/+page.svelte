<script>

    import {auth} from "$lib/user.ts";
    import {page} from "$app/stores";

    export let data;

    $: console.log(data);

    $auth = data.user;

    console.log(auth);
</script>

<main class="p-4 relative">
    {#if ($page.url.searchParams.has("created"))}
        <span class="text-green-500 flex items-center justify-center h-full">Created</span>
    {/if}

    {#if (auth)}
    <div class="absolute top-4 right-4 z-10">
        <button class="bg-blue-500 text-white px-4 py-2 rounded" onclick="window.location.href='/createFeed';">
            Create new Blog
        </button>
    </div>
        {:else}
        <div class="absolute top-4 right-4 z-10">
            <button class="bg-blue-500 text-white px-4 py-2 rounded" onclick="window.location.href='/createFeed';" disabled>
                Create new Blog
            </button>
        </div>
    {/if}


    <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-4 lg:grid-cols-3 xl:grid-cols-4 gap-4 mx-auto max-w-screen-xl mt-12 relative">
        {#each data.posts as post (post.id)}
            <div class="flex flex-col rounded-md shadow-md overflow-hidden">
                {#if post.imageUrl}
                    <img class="w-full h-48 object-cover" src={post.imageUrl} alt={post.title} />
                {:else}
                    <div class="bg-gradient-to-br from-blue-500 to-blue-200 h-48"></div>
                {/if}
                <div class="p-6 flex-grow">
                    <h2 class="text-xl font-bold mb-2">{post.title}</h2>
                    <p class="text-gray-600">{post.description}</p>
                    <!-- Weitere Informationen oder Aktionen können hier hinzugefügt werden -->
                </div>
            </div>
        {/each}
    </div>
</main>


