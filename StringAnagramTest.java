import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class StringAnagramTest {

    @Test
    public void testAreAnagrams_SimpleTrue() {
        assertTrue(StringAnagram.areAnagrams("Listen", "Silent"));
    }

    @Test
    public void testAreAnagrams_IgnoresSpacesAndCase() {
        assertTrue(StringAnagram.areAnagrams("Conversation", "Voices rant on"));
    }

    @Test
    public void testAreAnagrams_RepeatedCharsTrue() {
        assertThat(StringAnagram.areAnagrams("aabbcc", "abcabc"), is(true));
    }

    @Test
    public void testAreAnagrams_NotAnagrams() {
        assertFalse(StringAnagram.areAnagrams("Hello", "World"));
    }

    @Test
    public void testAreAnagrams_DifferentLengths() {
        assertFalse(StringAnagram.areAnagrams("abc", "ab"));
    }

    @Test
    public void testAreAnagrams_NullInputs() {
        assertFalse(StringAnagram.areAnagrams(null, "abc"));
        assertFalse(StringAnagram.areAnagrams("abc", null));
        assertFalse(StringAnagram.areAnagrams(null, null));
    }
}