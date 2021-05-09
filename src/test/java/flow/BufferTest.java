package flow;

import io.reactivex.Observable;
import org.junit.jupiter.api.Test;
import scheduler.PrintThread;

import java.util.List;
import java.util.concurrent.TimeUnit;

class BufferTest extends PrintThread {

	@Test
	void bufferTest() throws InterruptedException {
		String[] data = {"1", "2", "3", "4", "5", "6"};

		Observable<String> earlySource = Observable.fromArray(data)
												   .take(3)
												   .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), (a, b) -> a);

		Observable<String> middleSource = Observable.just(data[3])
													.zipWith(Observable.timer(300L, TimeUnit.MILLISECONDS), (a, b) -> a);

		Observable<String> lateSource = Observable.just(data[4], data[5])
												  .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), (a, b) -> a);

		Observable<List<String>> source = Observable.concat(earlySource, middleSource, lateSource)
													.buffer(2,3); // 데이터를 2개씩 모으고 1개를 스킵한다.

		source.doOnNext(this::printTime).subscribe();
		Thread.sleep(1000L);
	}
}
