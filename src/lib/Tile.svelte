<script>
       /**
	 * @type {string | null}
	 */
    export let tic = null;
</script>
<div class="tile">
    {#if tic === "X"}
        <div class="X">X</div>
    {:else if tic === "O"}
        <div class="O">O</div>
    {/if}
</div>

<style>
    .tile {
        background-color: bisque;
        height: 20vw;
        width: 20vw;

    }
    .X {
        font-size: 15vw;
        font-style: normal;
        font-family: cursive;
    }
    .O {
        font-size: 15vw;
        font-style: normal;
        font-family: cursive;
    }
</style>
