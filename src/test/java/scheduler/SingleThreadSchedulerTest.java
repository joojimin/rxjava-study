package scheduler;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import org.junit.jupiter.api.Test;

public class SingleThreadSchedulerTest extends PrintThread{

	@Test
	void singleThreadTest() throws InterruptedException {
		Observable<Integer> numbers = Observable.range(100, 5);
		Observable<String> chars = Observable.range(0, 5).map(num -> num + "1");

		numbers.subscribeOn(Schedulers.single())
			   .doOnNext(this::printTime)
			   .subscribe();

		chars.subscribeOn(Schedulers.single()) // 생성된 같은 스레드를 쓴다.
			 .doOnNext(this::printTime)
			 .subscribe();

		Thread.sleep(1000L);
	}
}
