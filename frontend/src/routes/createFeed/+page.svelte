<script>
    export let form;
</script>

<div class="flex items-center pb-80 justify-center h-screen">
    <form method="post" class="w-full max-w-md bg-white shadow-md rounded-md p-6">
        <div class="mb-4">
            <label class="block text-gray-700 text-sm font-semibold mb-2">
                Title
                <input name="title" class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:border-indigo-500" type="text" required>
            </label>
        </div>

        <div class="mb-4">
            <label class="block text-gray-700 text-sm font-semibold mb-2">
                Short Description
                <input name="shortDescription" class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:border-indigo-500" type="text" required>
            </label>
        </div>

        <div class="mb-4">
            <label class="block text-gray-700 text-sm font-semibold mb-2">
                Content
                <textarea class="w-full pt-2 pl-2 pr-2 pb-2 border-2 rounded" id="content" name="content" rows="10"></textarea>
            </label>

        {#if form}
        <span class="text-red-600">{(form.error)}</span>
            {/if}
        </div>
        <button class="w-full bg-indigo-500 text-white py-2 rounded-md hover:bg-indigo-600 focus:outline-none" type="submit">
            Submit Content
        </button>

    </form>
</div>