package flow;

import io.reactivex.Observable;
import org.junit.jupiter.api.Test;
import scheduler.PrintThread;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

class ThrottleTest extends PrintThread {

	@Test
	void throttleTest() throws InterruptedException {
		String[] data = {"1", "2", "3", "4", "5", "6"};

		Observable<String> earlySource = Observable.just(data[0])
												   .zipWith(Observable.timer(100L, TimeUnit.MILLISECONDS), (a, b) -> a);

		Observable<String> middleSource = Observable.just(data[1])
													.zipWith(Observable.timer(300L, TimeUnit.MILLISECONDS), (a, b) -> a);

		Observable<String> lateSource = Observable.just(data[2], data[3], data[4], data[5])
												  .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), (a, b) -> a);

		Observable<String> source = Observable.concat(earlySource, middleSource, lateSource)
											  .throttleFirst(200L, TimeUnit.MILLISECONDS);

		source.doOnNext(this::printTime)
			  .subscribe();
		Thread.sleep(1000L);

		// 100L "1" // throttleFirst(200L)
		// 400L "2" // throttleFirst(400L)
		// 500L "3" // 발행 ㄴㄴ
		// 600L "4" // throttleFirst(600L)
		// 700L "5" // 발행 ㄴㄴ
		// 800L "6"	// throttleFirst(800L)
	}
}
