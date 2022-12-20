<script>
	// import init, {greet} from 'engine';
	import 'papercss/dist/paper.min.css'

	import {Button} from 'spaper'

    let winMap = [
		[0, 1, 2],
		[3, 4, 5],
		[6, 7, 8], 
		[0, 3, 6],
		[1, 4, 7],
		[2, 5, 8],
		[0, 4, 8],
		[2, 4, 6]
	];

	let isXTurn = true;

	let boardState = Array(9).fill('');

	let mathu = "the best";
	let vithu = "the worst";


	// async function loadWasm() {
	// 	const wasmInit = await init();
		
	// 	greet();
	// }
	// loadWasm();

	/**
	 * @param {string[]} toes
	 * @param {number} index
	 */
	function checkBoard(toes, index) {
		winMap
			.filter((winPattern) => winPattern.find((i) => i === index))
			.forEach((winPattern) => {
				if (
					toes[winPattern[0]] === toes[winPattern[1]] &&
					toes[winPattern[1]] === toes[winPattern[2]] &&
                    toes[winPattern[0]] !== ''
				) {
                    console.log(winPattern);
                    alert(`${toes[winPattern[0]]} Wins!  ${winPattern}`);
					if (confirm("Would you like to start a new game?")){
						resetBoard();
					}

				}
			});
	}

	function resetBoard() {
		boardState = Array(9).fill('');
	}


	function foo(){
		console.log("page is fully loaded");

	}


</script>

<svelte:window on:load={foo}/>

<!-- <h1>Tic Tac Toe</h1>
<h1>Mathu is {vithu}</h1>
<h2>Vithu is {mathu}</h2> -->
<!-- <button on:click={resetBoard}> New Game </button> -->

<div class="game row">
	<div class="board {isXTurn ? 'x_turn' : 'o_turn'}">
		{#each boardState as toe, i}
			<div
			class="col border border-primary shadow shadow-hover tile background-secondary {toe}"
			on:click={() => {
				boardState[i] = isXTurn ? 'X' : 'O';
				isXTurn = !isXTurn;
				checkBoard(boardState, i);
			}}
			/>
		{/each}
	</div>
</div>
<div class="row flex-center">
	<Button class="btn-success btn-large sm-6 col" on:click={resetBoard}>New Game</Button>
</div>

<style>
	.game {
		display: flex;
		justify-content: center;
	}
	.board {
		display: grid;
		grid-gap: 1vmin;
		grid-template:
			'tile tile tile'
			'tile tile tile'
			'tile tile tile';
		/* background-color: azure; */
	}

    .x_turn > .tile:not(.X):not(.O):hover::before {
        content: 'X';
    }

    .o_turn > .tile:not(.X):not(.O):hover::before {
        content: 'O';
    }

	.tile {
        display: flex;
        justify-content: center;
        align-content: center;
		height: 30vmin;
		width: 30vmin;
	}

    .tile:not(.X):not(.O):hover{
        opacity: 25%;
    }

    .tile::before {
        font-size: 20vmin;
		font-style: normal;
		font-family: cursive;
    }

	.X::before {
		content: 'X';
		
	}
	.O::before {
		content: 'O';
		/* font-size: 15vw;
		font-style: normal;
		font-family: cursive; */
	}
</style>
