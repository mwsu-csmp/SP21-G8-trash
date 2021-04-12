import org.junit.Test;
import static org.junit.Assert.*;

public class AppTest {

    @Test
    public void TestAppStructure(){
        assertEquals("No Fields should be public", 0, KhanCards.class.getFields().length);
    }
}
