import java.util.ArrayList;
import java.util.Locale;

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

    public Card topCard() throws ArrayIndexOutOfBoundsException{
        if(!cards.isEmpty()) return cards.get(cards.size()-1);
        else throw new ArrayIndexOutOfBoundsException("Cannot look at the top card in an empty pile.");
    }

    public Card replaceTop(Card card) throws ArrayIndexOutOfBoundsException{
        if(!cards.isEmpty()){
            cards.add(1, card);
            return cards.remove(0);
        }
        else throw new ArrayIndexOutOfBoundsException("Cannot replace a card that does not exist.");
    }

    public String getImageDir(){
        if(cards.size() < 1) return FilePaths.EMPTY_CARD_AREA;
        return this.topCard().getImageDir();
    }
}
