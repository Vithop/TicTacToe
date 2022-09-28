<script>
    import { afterUpdate } from 'svelte';

    import Tile from "../../lib/Tile.svelte";

    let tac = true;


    let toes = [
        undefined, undefined, undefined,
        undefined, undefined, undefined,
        undefined, undefined, undefined
    ]

    let winMap = [
        [0,1,2],
        [3,4,5],
        [6,7,8],
        [0,3,6],
        [1,4,7],
        [2,5,8],
        [0,4,8],
        [2,4,6],
    ]

    /**
	 * @param {any[] | undefined} [toes]
	 */
    function checkBoard(toes) {
        winMap.forEach(winPattern => {
            if(toes[winPattern[0]] && toes[winPattern[1]] && toes[winPattern[2]]){
                if(toes[winPattern[0]] === toes[winPattern[1]] && toes[winPattern[1]] === toes[winPattern[2]]){
                    console.log(winPattern)
                    return alert(`${toes[winPattern[0]]} Wins!  ${winPattern}` );
                    
                }
            }
        });
        
    }

</script>

<h1>Tic Tac Toe</h1>
<div class="game">
    <div class="board">
        {#each toes as toe, i }
        <div  on:click={() => {
            toes[i] = tac ? "X" : "O";
            tac = !tac;
            checkBoard(toes)
        }}>
            <Tile tic={toe}/>        

        </div>
        {/each}  
    </div>
</div>

<style>
    .game {
        display: flex;
        justify-content: center;
    }
    .board {
        display: grid;
        grid-gap: 1em;
        grid-template: 
            'tile tile tile'
            'tile tile tile'
            'tile tile tile';
        background-color: brown;
    }
</style>