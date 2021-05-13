package CommonTest;

import io.reactivex.Observable;
import org.junit.jupiter.api.Test;
import scheduler.PrintThread;

import java.util.concurrent.TimeUnit;

class TestAsyncExampleTest extends PrintThread {

	@Test
	void asyncExampleTest(){
		Observable<Integer> source = Observable.interval(100L, TimeUnit.MILLISECONDS)
											   .take(5)
											   .map(Long::intValue);

		source.doOnNext(this::printTime)
			  .test()
			  .awaitDone(1L, TimeUnit.SECONDS)
			  .assertResult(0,1,2,3,4);
	}
}
