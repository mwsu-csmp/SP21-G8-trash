import java.util.Random;

public class Deck extends CardCollection{
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
}
