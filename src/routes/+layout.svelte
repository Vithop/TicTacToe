<div class="row flex-center">
	<nav class="margin background-primary-light">
		<div class="collapsible">
			<input id="collapsible1" type="checkbox" name="collapsible1" />
			<label for="collapsible1">
				<div class="bar1" />
				<div class="bar2" />
				<div class="bar3" />
			</label>
			<div class="collapsible-body">
				<ul class="inline">
					<li><a href="/">Home</a></li>
					<li><a href="/About">About</a></li>
					<li><a href="/TicTacToe">Tic Tac Toe</a></li>
	
				</ul>
			</div>
		</div>
	</nav>
</div>
<div class="row flex-center margin-top-large">
	<div class="col">
		<slot />
	</div>
</div>
