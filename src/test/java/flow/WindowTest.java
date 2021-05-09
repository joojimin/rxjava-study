package flow;

import io.reactivex.Observable;
import org.junit.jupiter.api.Test;
import scheduler.PrintThread;

import java.util.concurrent.TimeUnit;

class WindowTest extends PrintThread {

	@Test
	void windowTest() throws InterruptedException {
		String[] data = {"1", "2", "3", "4", "5", "6"};

		Observable<String> earlySource = Observable.fromArray(data)
												   .take(3)
												   .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), (a, b) -> a)
												   .doOnNext(value1-> this.printTime("#1 : " + value1));

		Observable<String> middleSource = Observable.just(data[3])
													.zipWith(Observable.timer(300L, TimeUnit.MILLISECONDS), (a, b) -> a)
													.doOnNext(value1-> this.printTime("#2 : " + value1));

		Observable<String> lateSource = Observable.just(data[4], data[5])
												  .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), (a, b) -> a)
												  .doOnNext(value1-> this.printTime("#3 : " + value1));

		Observable<Observable<String>> source = Observable.concat(earlySource, middleSource, lateSource)
														  .window(3);

		source.subscribe(observer -> {
			printTime("New Observable Started!!");
		});
		Thread.sleep(1000L);
	}
}
