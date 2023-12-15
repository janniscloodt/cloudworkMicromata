<script lang="ts">
    let password = "";
    let passwordConfirm = "";

    $: passwordValid = password === passwordConfirm
</script>


<div class="flex items-center pb-80 justify-center h-screen">
    <form method="post" class="w-full max-w-md bg-white shadow-md rounded-md p-6">
        <div class="mb-4">
            <label class="block text-gray-700 text-sm font-semibold mb-2">
                Username
                <input name="username" class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:border-indigo-500" type="text" required>
            </label>
        </div>

        <div class="mb-4">
            <label class="block text-gray-700 text-sm font-semibold mb-2">
                E-Mail
                <input name="email" class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:border-indigo-500" type="email" required>
            </label>
        </div>

        <div class="mb-4">
            <label class="block text-gray-700 text-sm font-semibold mb-2">
                Password
                <input name="password" bind:value={password} class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:border-indigo-500" type="password" required>
            </label>
        </div>

        <div class="mb-4">
            <label class="block text-gray-700 text-sm font-semibold mb-2">
                Repeat Password
                <input name="passwordConfirm" bind:value={passwordConfirm} class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:border-indigo-500" type="password" required>
            </label>
            {#if !passwordValid}
                <span class="text-red-600">Passwords are not identical</span>
            {/if}
        </div>

        <button class="w-full bg-[#5e60e4] text-white py-2 rounded-md hover:bg-indigo-600 focus:outline-none disabled:bg-gray-400"
                type="submit"
                disabled={!passwordValid}>
            Sign Up
        </button>

        <div class="pt-3">
            Already have an Account? Click
            <a href="../login" class="text-blue-700 underline">here</a>
        </div>


    </form>
</div>