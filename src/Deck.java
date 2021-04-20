import java.util.Random;

/** A standard deck of playing cards, minus Jokers
 *
 */
public class Deck extends CardCollection {
    public Deck(){
        for(int i = 2; i <= 14; i++){
            cards.add(new Card(i, Suit.Clubs));
            cards.add(new Card(i, Suit.Spades));
            cards.add(new Card(i, Suit.Diamonds));
            cards.add(new Card(i, Suit.Hearts));
        }
    }

    public Deck(boolean shuffled){
        this();
        if (shuffled) shuffle();
    }

    public void shuffle(){
        Random rng = new Random();
        for(int i = 0; i < 200; i++){
            cards.add(cards.remove(rng.nextInt(cards.size())));
        }
    }

    public String getImageDir() {
        if(cards.size() < 1) return FilePaths.EMPTY_CARD_AREA;
        else return FilePaths.CARD_BACK;
    }
}
