<script context="module" lang="ts">
    // Import any types or modules you need
</script>

<script>
    export let articles = [];

    // Add any other necessary script logic
</script>

<style>
    /* Add your styling here */
</style>

<div class="container mt-5">
    <div class="row">
        {#each articles as article (article.title)}
            <div class="col-md-4 mb-4">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">{article.title}</h5>
                        <p class="card-text">{article.content}</p>
                        <!-- Add other properties if needed -->
                    </div>
                </div>
            </div>
        {/each}
    </div>
</div>
