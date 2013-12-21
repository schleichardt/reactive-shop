package concurrent

import play.libs.F.Promise
import java.lang.{Iterable => JIterable}
import java.util.{List => JList}
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import scala.concurrent.Future
import play.libs.{F, HttpExecution}

private[concurrent] object PromisesImpl {
  def sequence[A](promises: JIterable[Promise[A]]): Promise[JIterable[A]] = {
    val iterableOfFutures: Iterable[Future[A]] = promises.map(_.wrapped)
    implicit val context = HttpExecution.defaultContext
    val futureOfIterables: Future[JIterable[A]] = Future.sequence(iterableOfFutures).map(_.asJava)
    Promise.wrap(futureOfIterables)
  }

  def sequence[A](promises: JList[Promise[A]]): Promise[JList[A]] = {
    val promiseOfIterable: Promise[JIterable[A]] = sequence(promises.toIterable.asJava)
    //TODO define conversion from scala function to play function
    promiseOfIterable.map(new F.Function[JIterable[A], JList[A]] (){
      def apply(a: JIterable[A]) = a.toList
    })
  }


}
