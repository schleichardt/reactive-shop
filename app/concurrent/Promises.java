package concurrent;

import com.google.common.base.Function;
import play.libs.F;
import play.libs.F.Promise;
import static com.google.common.collect.Lists.transform;

import java.util.List;

//the methods are a candiate for sphere.util.Async
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

    //TODO generate more methods for more parameters
    public static <A, B, C> Promise<C> combine(final Promise<A> promiseA, final Promise<B> promiseB, final F.Function2<A, B, C> f) {
        return promiseA.flatMap(new F.Function<A, Promise<C>>() {
            @Override
            public Promise<C> apply(final A a) throws Throwable {
                return promiseB.map(new F.Function<B, C>() {
                    @Override
                    public C apply(final B b) throws Throwable {
                        return f.apply(a, b);
                    }
                });
            }
        });
    }
}
