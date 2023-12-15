<script lang="ts">
    import {onMount} from "svelte";

    let form: HTMLFormElement;

    onMount(() => {
        form.submit();
    })
</script>

<form method="post" bind:this={form}></form>