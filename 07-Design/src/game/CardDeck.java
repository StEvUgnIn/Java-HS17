package game;

import java.util.ArrayList;
import java.util.Random;

public class CardDeck {
    public static final int CARDS_PER_SUIT = 13;
	private static Random rd;
	private ArrayList<Card> deck;

    CardDeck() {
	    deck = new ArrayList<Card>();
    }

    public void fill () {
		for (Suit s : Suit.values()) {
		    for (int i = 0; i <= CARDS_PER_SUIT; i++) {
			    deck.add(new Card(i, s));
		    }
	    }
	    shuffle();
    }

	private void shuffle() {
		ArrayList<Card> shuffleDeck = new ArrayList<>();
		while (deck.size() > 0) {
			shuffleDeck.add(deck.remove(rd.nextInt(deck.size())));
		}

		while (shuffleDeck.size() > 0) {
			deck.add(deck.remove(rd.nextInt(shuffleDeck.size())));
		}
	}

	public Card take () {
		return deck.remove(0);
	}

	public boolean add (Card card) {
		return deck.add(card);
	}

	public boolean hasCardsFromEachSuit (int n, Suit suit) {
    	int count = 0;
    	for (Card c : deck) {
    		if (c.getSuit() == suit) {
			    ++count;
		    }
	    }
		return count >= n;
	}

	public boolean hasCardsFromEachSuit(int n) {
		for (Suit s : Suit.values()) {
			if(!hasCardsFromEachSuit(n, s)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString () {
		String s = "";
		for (Card c : deck) {
			s += c + "\n";
		}

		return s;
	}

	public enum Suit {SPADE,HEART,CLUB,DIAMOND};
}
