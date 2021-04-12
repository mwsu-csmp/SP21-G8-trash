public class Pile extends CardCollection {
    private final int maxSize;
    public Pile(){
        this.maxSize = 52;
    }

    public Pile(int maxSize){
        this.maxSize = maxSize;
    }

    public int getMaxSize(){ return maxSize; }

    @Override
    public void add(Card card) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
        if( cards.size() < maxSize ) {
            super.add(card);
        }
        else throw new ArrayIndexOutOfBoundsException("Cannot add cards to a full pile.");
    }
}
