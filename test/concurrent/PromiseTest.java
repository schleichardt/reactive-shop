package concurrent;

import org.junit.Test;
import static org.fest.assertions.Assertions.assertThat;
import static play.libs.F.*;

public class PromiseTest {
    @Test
    public void testSequenceOfDifferentPromises() throws Exception {
        Promise<String> stringPromise = Promise.pure("aString");
        Promise<Integer> IntPromise = Promise.pure(5);
        Promise<String> promise = Promises.combine(stringPromise, IntPromise, new Function2<String, Integer, String>() {
            @Override
            public String apply(String s, Integer i) throws Throwable {
                return s + i;
            }
        });
        assertThat(promise.get(20)).isEqualTo("aString5");
    }
}
