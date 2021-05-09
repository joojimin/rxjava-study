package errorhandling;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import org.junit.jupiter.api.Test;
import scheduler.PrintThread;

import java.util.concurrent.TimeUnit;

class RetryWhenTest extends PrintThread {

	@Test
	void retryWhenTest(){
		Observable.create((ObservableEmitter<String> emitter) -> emitter.onError(new RuntimeException("always fails")))
				  .retryWhen(attempts -> attempts.zipWith(Observable.range(1, 3), (n, i) -> i)
												 .flatMap(i -> {
													 printTime("delay retry by " + i + " seconds");
													 return Observable.timer(i, TimeUnit.SECONDS);
												 }))
				  .blockingForEach(this::printTime);
	}
}
