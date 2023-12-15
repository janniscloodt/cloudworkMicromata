<script lang="ts">
    import {auth} from "$lib/user";
    import img from '$lib/assets/EmployeePictogram.png';

    export let data;
</script>

<div class="flex items-center justify-center h-screen pb-80">
    <form method="post" class="w-full max-w-md bg-white shadow-md rounded-md p-6">
        <div class="flex items-center mb-4">
            <img src={img} alt="User Icon" class="w-8 h-8 mr-4">

            <hr class="border-t border-gray-400 w-full border-solid h-0.5">
        </div>

        <div class="mb-4">
            <p class="text-xl font-bold mb-2">Guten Tag {$auth.username}</p>
            <hr>
            <p class="text-lg">E-Mail: {$auth.email}</p>
        </div>
    </form>
</div>
