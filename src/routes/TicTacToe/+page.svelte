<script>
	
    let tic = [
		[0, 1, 2],
		[3, 4, 5],
		[6, 7, 8],
		[0, 3, 6],
		[1, 4, 7],
		[2, 5, 8],
		[0, 4, 8],
		[2, 4, 6]
	];

	let tac = true;

	let toes = Array(9).fill('');


	/**
	 * @param {string[]} toes
	 * @param {number} index
	 */
	async function checkBoard(toes, index) {
		tic
			.filter((winPattern) => winPattern.find((i) => i === index))
			.forEach((winPattern) => {
				if (
					toes[winPattern[0]] === toes[winPattern[1]] &&
					toes[winPattern[1]] === toes[winPattern[2]] &&
                    toes[winPattern[0]] !== ''
				) {
                    console.log(winPattern);
                    return alert(`${toes[winPattern[0]]} Wins!  ${winPattern}`);
				}
			});
	}
</script>

<h1>Tic Tac Toe</h1>
<div class="game">
	<div class="board">
		{#each toes as toe, i}
			<div
				class="tile {toe}"
				on:click={() => {
					toes[i] = tac ? 'X' : 'O';
					tac = !tac;
					checkBoard(toes, i);
				}}
			/>
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

	.tile {
        display: flex;
        justify-content: center;
        align-content: center;
		background-color: bisque;
		height: 20vw;
		width: 20vw;
	}
	.X::before {
		content: 'X';
		font-size: 15vw;
		font-style: normal;
		font-family: cursive;
	}
	.O::before {
		content: 'O';
		font-size: 15vw;
		font-style: normal;
		font-family: cursive;
	}
</style>
