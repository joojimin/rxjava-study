package condition;

import io.reactivex.Observable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

class TakeUntilTest {

	private long startTime;

	@BeforeEach
	void setUpTime(){
		startTime = System.currentTimeMillis();
	}

	private void printTime(Object data){
		long endTime = System.currentTimeMillis();
		String threadName = Thread.currentThread().getName();
		System.out.println("[" + threadName + "]"
						   + " time = " + (endTime-startTime)
						   + ", data = " + data);
	}

	@Test
	void takeUntilTest() throws InterruptedException {
		String[] data = {"1", "2", "3", "4", "5", "6"};

		Observable<String> sources = Observable.fromArray(data)
											   .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS),
														(val, notUsed)->val)
											   .takeUntil(Observable.timer(500L, TimeUnit.MILLISECONDS));

		sources.doOnNext(this::printTime)
			   .subscribe();
		Thread.sleep(1000L);
	}
}
