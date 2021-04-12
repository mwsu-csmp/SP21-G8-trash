import org.junit.Test;
import static org.junit.Assert.*;

public class CardTest {

    @Test
    public void TestCardBasicStructure(){
        assertEquals("No Fields should be public", 0, Card.class.getFields().length);
    }
}
