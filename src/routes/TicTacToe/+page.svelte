<script>
	// import init, {greet} from 'engine';
	import 'papercss/dist/paper.min.css';

	import { Button } from 'spaper';
	import Game, {Status} from "../../lib/Game.ts";

	let game = new Game();

	/**
	 * @param {number} i
	 */
	function drawTicTac(i) {
		game.executeMove(i);
		// Force Svelte to re-render by assigning variable to itself since Svelte 
		// doesn't know `executeMove` causes a mutation
		game = game;
		checkBoard(game.status);			
	}

	function confirmNewGame(){
		if (confirm('Would you like to start a new game?')) {
			resetBoard();
		}
	}

	/**
	 * @param {string[]} toes
	 * @param {number[]} winPattern
	 */
	function showWinScreen(status) {
		alert(`${status}  ${status.winPositions}`);
		confirmNewGame()
	}

	function showDrawScreen() {
		alert(`😺😺😺 It's cats game! 😺😺😺`);
		confirmNewGame()
	}

	function checkBoard(status) {	
		if (status === Status.Draw) {
			showDrawScreen();
		}
		else if(status instanceof Status.XWon || status instanceof Status.OWon){
			showWinScreen(status);
		}
	}

	function resetBoard() {
		game = new Game();
	}

	function foo() {
		console.log('page is fully loaded');
	}
</script>

<svelte:window on:load={foo} />

<div class="game row">
	<div class="board {game.isXTurn ? 'x_turn' : 'o_turn'}">
		{#each game.boardState as toe, i}
			<div
				class="col border border-primary shadow shadow-hover tile background-secondary {toe}"
				on:click={()=>drawTicTac(i)}
				on:keydown={()=>{}}
			/>
		{/each}
	</div>
</div>
<div class="row flex-center">
	<Button class="btn-success col-12 col" on:click={resetBoard}>New Game</Button>
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
		height: 20vmin;
		width: 20vmin;
	}

	/* .tile:not(.X):not(.O):hover {
		opacity: 25%;
	} */

	.tile::before {
		font-size: 15vmin;
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
