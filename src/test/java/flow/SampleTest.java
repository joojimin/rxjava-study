package flow;

import io.reactivex.Observable;
import org.junit.jupiter.api.Test;
import scheduler.PrintThread;

import java.util.concurrent.TimeUnit;

class SampleTest extends PrintThread {

	@Test
	void sampleTest() throws InterruptedException {
		String[] data = {"1", "7", "2", "3", "6"};

		Observable<String> earlySource = Observable.fromArray(data)
												   .take(4)
												   .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), (a,b)->a);

		Observable<String> lateSource = Observable.just(data[4])
												  .zipWith(Observable.timer(300L, TimeUnit.MILLISECONDS), (a,b)->a);

		Observable<String> source = Observable.concat(earlySource, lateSource)
											  .sample(300L, TimeUnit.MILLISECONDS, true);
		// 100L "1"
		// 200L "7"  >> sample #1 >> 정확하게 시간은 아니기때문에 7이 발행
		// 300L "2"
		// 400L "3"  >> sample #2
		// 700L "6"  >> true

		source.doOnNext(this::printTime)
			  .subscribe();
		Thread.sleep(3000L);
	}
}
