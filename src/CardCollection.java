import java.util.ArrayList;

public abstract class CardCollection {
    protected ArrayList<Card> cards = new ArrayList();

    public Card draw() throws ArrayIndexOutOfBoundsException{
        if(!cards.isEmpty()) return cards.remove(cards.size()-1);
        else throw new ArrayIndexOutOfBoundsException("Cannot draw from an empty pile.");
    }

    public void add(Card card) throws IllegalArgumentException{
        if(card == null) throw new IllegalArgumentException("Cannot add null cards to a pile.");
        else cards.add(card);
    }

    public Card get(Card card) throws IllegalArgumentException{
        if(cards.contains(card)) return cards.remove(cards.indexOf(card));
        else throw new IllegalArgumentException("Cannot get a card from a pile if that pile doesn't have that card.");
    }

    public ArrayList<Card> show(){
        return new ArrayList<Card>(cards);
    }

    public boolean has(Card card){
        return cards.contains(card);
    }

    public int size(){
        return cards.size();
    }
}
