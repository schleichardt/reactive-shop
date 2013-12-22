package concurrent;

import com.google.common.base.Function;
import play.libs.F.Promise;
import static com.google.common.collect.Lists.transform;

import java.util.List;

public final class Promises {
    private Promises() {
    }

    //needed as workaround for https://github.com/playframework/playframework/issues/1595
    public static <A> Promise<Iterable<A>> sequence(Iterable<Promise<A>> promises) {
        return PromisesImpl$.MODULE$.sequence(promises);
    }

    //needed as workaround for https://github.com/playframework/playframework/issues/1595
    public static <A> Promise<List<A>> sequence(List<Promise<A>> promises) {
        return PromisesImpl$.MODULE$.sequence(promises);
    }

    /**
     * Transforms a <code>fromList</code> with <code>function</code> to list of promises and aggregates it to a single Promise.
     */
    public static <A, B> Promise<List<B>> sequence(List<A> fromList, Function<? super A, Promise<B>> function) {
        final List<Promise<B>> listOfPromises = transform(fromList, function);
        return Promises.sequence(listOfPromises);
    }
}
