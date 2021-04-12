import org.junit.Test;
import static org.junit.Assert.*;

public class CollectionTest {

    @Test
    public void TestCollectionBasicStructure(){
        assertEquals("No Fields should be public", 0, Deck.class.getFields().length);
        assertEquals("No Fields should be public", 0, Pile.class.getFields().length);
    }
}
