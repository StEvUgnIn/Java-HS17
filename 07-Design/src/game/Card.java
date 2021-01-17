package game;

public class Card {

    private int value;
    private CardDeck.Suit suit;

    public Card(int value, CardDeck.Suit suit) {
        this.value = value;
        this.suit  = suit;
    }

    public CardDeck.Suit getSuit() {
        return suit;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        String output;
        switch (value) {
            case 1:
                output = "Ace";
                break;
            case 11:
                output = "Jack";
                break;
            case 12:
                output = "Queen";
                break;
            case 13:
                output = "King";
                break;
            default:
                output = Integer.toString(value);
        }
	    output += " of " + suit;

	    return output;
    }
}
