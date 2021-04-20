/** Specification for playing card suits
 *
 */
public enum Suit {
    Hearts("Hearts"), Clubs("Clubs"), Diamonds("Diamonds"), Spades("Spades");
    private String stringVersion;
    Suit(String stringVersion){
        this.stringVersion = stringVersion;
    }

    /** returns string version
     *
     * @return string version of the rating
     */
    @Override
    public String toString() {
        return stringVersion;
    }
}
