package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

public class Game extends ApplicationAdapter {

	// FOR EASIER READABILITY, ASSIGN VALUES TO THE SUITS AND FACE CARDS
	public final int CLUBS = 1;
	public final int DIAMONDS = 2;
	public final int HEARTS = 3;
	public final int SPADES = 4;

	public final int JACK = 11;
	public final int QUEEN = 12;
	public final int KING = 13;


	Deck deck;
	Tableau tableau;
	Stock stock;
	Waste waste;

	private SpriteBatch batch;

	private Texture bg;
    private Texture cardBackTexture;

	private TextureRegion[][] cardTextureRegions;

	public void loadTextures()
	{
		bg = new Texture(Gdx.files.internal("textures/Backgrounds/background_2.png"));
		cardBackTexture = new Texture(Gdx.files.internal("textures/Standard/solitaire/individuals/cardback/card_back.png"));
        Texture cardTextures = new Texture(Gdx.files.internal("textures/Standard/solitaire/all_cards.png"));

		cardTextureRegions = new TextureRegion[4][13];

		// EXTRACT ALL TEXTURES FROM SPRITE SHEET AND PUT INTO TEXTURE ARRAY
		for (int row = 0; row < 4; row++)
		{
			for (int col = 0; col < 13; col++)
			{
				cardTextureRegions[row][col] = new TextureRegion(cardTextures, col*122, row*168, 122, 168);
			}
		}

	}
	
	@Override
	public void create ()
	{
		loadTextures();
		deck = new Deck();
		tableau = new Tableau();
		stock = new Stock();
		waste = new Waste();
		deck.shuffle();

		setupGame();

		batch = new SpriteBatch();

	}

	@Override
	public void render ()
	{
		batch.begin();
		renderGame();
		batch.end();

		// HANDLE INPUT FROM USER
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
		{
			shuffleThroughStock();
		}
	}

	public void shuffleThroughStock()
	{
		// IF STOCK PILE IS EMPTY WHEN TRYING TO SHUFFLE THROUGH, MOVE ALL THE WASTE BACK INTO STOCK
		if (stock.getStock().isEmpty())
		{
			for (int i = waste.getWaste().size()-1; i >= 0; i--)
			{
				stock.addCard(waste.getWaste().get(i));
			}
			waste.getWaste().clear();
			return;
		}
		// IF THERE IS LESS THAN 3 CARDS IN THE STOCK PILE JUST PUT ALL THOSE CARDS INTO WASTE
		if (stock.getStock().size() < 3)
		{
			waste.getWaste().addAll(stock.getStock());
			stock.getStock().clear();
			return;
		}

		for (int i = 0; i < 3; i++)
		{
			// GET TOP CARD FROM STOCK
			Card currentStockCard = stock.getStock().get(stock.getStock().size()-1);
			//printCardData(currentStockCard);
			// MOVE THAT TOP CARD FROM STOCK INTO THE WASTE PILE
			waste.addCard(currentStockCard);
			// DELETE THE TOP CARD FROM THE STOCK SINCE IT WAS MOVED TO THE WASTE
			stock.getStock().remove(stock.getStock().size()-1);
		}
		//System.out.println(stock.getStock().size());
		//System.out.println(waste.getWaste().size());

	}

	public void setupGame()
	{
		setupTableau();
		setupStockPile();
	}
	public void renderGame()
	{
		// RENDER BACKGROUND
		batch.draw(bg, 0, 0, 1280, 720);

		renderTableau();
		renderStockPile();
		renderWastePile();
	}

	public void setupTableau()
	{
		// CURRENT NUMBER OF CARDS FOR SPECIFIED COLUMN OF TABLEAU
		int cardCount = 1;
		// CURRENT INDEX OF CARD FROM DECK BEING USED
		int currentCardIndex = 51;

		Card currentCard;

		// STORE CARDS IN A TEMPORARY COLUMN
		ArrayList<Card> currentColumn;

		// SETUP TABLEAU
		for (int col = 0; col < 7; col++)
		{
			currentColumn = new ArrayList<>();
			for (int cards = 0; cards < cardCount; cards++)
			{

				currentCard = deck.getDeck().get(currentCardIndex);

				// LAST CARD IN THE COLUMN IS FACE UP
				if (cards == cardCount - 1)
				{
					currentCard.setFaceUp(true);
				}

				// ADD THE TOP CARD FROM THE DECK AND PLACE IT INTO THE COLUMN
				currentColumn.add(currentCard);

				// REMOVE THAT CARD FROM THE ACTUAL DECK SINCE IT WAS MOVED INTO THE COLUMN
				deck.getDeck().remove(currentCardIndex);

				currentCardIndex--;
			}
			// ADD CARDS TO A COLUMN
			tableau.getTableau().add(currentColumn);

			// AFTER A COLUMN IS MADE, THE CONSECUTIVE COLUMN HAS ONE MORE ADDITIONAL CARD THAN THE LAST ONE
			cardCount++;
		}
	}
	public void setupStockPile()
	{
		// PLACE ALL THE REST OF THE CARDS IN THE DECK INTO THE STOCK.
		for (int i = 0; i < deck.getDeck().size(); i++)
		{
			stock.addCard(deck.getDeck().get(i));
		}
		//System.out.println(stock.getStock().size());
		deck.getDeck().clear();
	}

	public void renderTableau()
	{
		Card currentCard;
		float xPos = 160;
		float yPos = 720/2f;
		for (int col = 0; col < tableau.getTableau().size(); col++)
		{
			for (int card = 0; card < tableau.getTableau().get(col).size(); card++)
			{
				currentCard = tableau.getTableau().get(col).get(card);

				// IF THE CURRENT CARD IS BEFORE THE LAST ONE IN THE DECK, IT IS FACE DOWN
				if (currentCard.getFaceUp())
				{
					batch.draw(cardTextureRegions[currentCard.getSuit()-1][currentCard.getValue()-1], xPos + col*144, yPos + card*-50);
				} else {
					batch.draw(cardBackTexture, xPos + col*144, yPos + card*-50);
				}
			}
		}
	}

	public void renderStockPile()
	{
		if (!stock.getStock().isEmpty())
		{
			int offset = 20;
			batch.draw(cardBackTexture, offset, 720-144 - offset);
		}
	}

	public void renderWastePile()
	{
		if (!waste.getWaste().isEmpty())
		{
			for (int i = 0; i < waste.getWaste().size(); i++)
			{
				Card topCard = waste.getWaste().get(i);
				batch.draw(cardTextureRegions[topCard.getSuit()-1][topCard.getValue()-1], 35*i+120, 720-175);

			}
			/*
			Card topCard = waste.getWaste().get(waste.getWaste().size()-1);
			batch.draw(cardTextureRegions[topCard.getSuit()-1][topCard.getValue()-1], 144+offset, 720-155 - offset);
			*/

		}

	}

	// DEBUGGING PURPOSES
	public void printCardData(Card c)
	{
		System.out.println("Suit: " + c.getSuit() + " Value: " + c.getValue());
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
