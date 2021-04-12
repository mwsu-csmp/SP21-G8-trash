public class Card {
    private final int value;
    private final Suit suit;
    private boolean faceUp = true;

    public Card(int value, Suit suit) throws IllegalArgumentException{
        if (value < 2 || 14 < value) throw new IllegalArgumentException("Cards must have a value between 2 and 14 (inclusive).");
        if (suit == null) throw new IllegalArgumentException("Cards must have a non-null suit value.");
        this.value = value;
        this.suit = suit;
    }

    public int getValue(){
        return value;
    }

    public Suit getSuit(){
        return suit;
    }

    public void reveal(){
        faceUp = true;
    }

    public void hide(){
        faceUp = false;
    }

    public boolean isFaceUp(){
        return faceUp;
    }

    @Override
    public String toString() {
        String localValue = String.valueOf(value);
        switch(localValue){
            case "11":
                localValue = "Jack";
                break;
            case "12":
                localValue = "Queen";
                break;
            case "13":
                localValue = "King";
                break;
            case "14":
                localValue = "Ace";
                break;
            default:
                break;
        }
        return localValue + " of " + suit.toString();
    }
}
