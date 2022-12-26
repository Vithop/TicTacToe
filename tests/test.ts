import { expect, test } from '@playwright/test';

test('index page has expected h1', async ({ page }) => {
	await page.goto('/');
	expect(await page.textContent('h1')).toBe('Welcome to SvelteKit');
});

test('test playing a tictactoe game', async ({ page }) => {
	await page.goto('/');
	await page.getByRole('link', { name: 'Tic Tac Toe' }).click();
	await page.locator('.board > div').first().click();
	await page.locator('.board > div:nth-child(2)').click();
	await page.locator('div:nth-child(5)').click();
	await page.locator('div:nth-child(6)').click();
	await page.locator('div:nth-child(4)').click();
	await page.locator('div:nth-child(9)').click();
	await page.locator('.board > div:nth-child(3)').click();
	await page.locator('div:nth-child(8)').click();

});

// test('index page has expected title "Hi Mathuran!"', async ({ page }) => {
// 	await page.goto('/');
// 	expect(await page.textContent('h1')).toBe('Hi Mathuran!');
// });