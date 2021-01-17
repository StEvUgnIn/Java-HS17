package game;

import java.util.Scanner;

public class CardPicker {

    public static void main(String[] args) {
	    Scanner in = new Scanner(System.in);
	    int numCards;
	    do {
		    System.out.print("Enter cards per suit: ");
		    numCards = in.nextInt();
	    } while (numCards <= 0 || numCards > CardDeck.CARDS_PER_SUIT);

	    CardDeck cards  = new CardDeck();
	    CardDeck result = new CardDeck();

	    cards.fill();
	    int pickCount = 0;

	    while(!result.hasCardsFromEachSuit(numCards)) {
		    Card card = cards.take();
		    ++pickCount;
	    }

    }

}
