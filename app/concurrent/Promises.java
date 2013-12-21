package concurrent;

import play.libs.F.Promise;

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
}
