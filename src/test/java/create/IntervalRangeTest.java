package create;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

public class IntervalRangeTest {

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
	void intervalRangeTest() throws InterruptedException {
		Observable.intervalRange(1, 5, 100L, 500L, TimeUnit.MILLISECONDS)
				  .map(data -> data * 100)
				  .doOnNext(this::printTime)
				  .subscribe();
		Thread.sleep(3000L);
	}

	@Test
	void intervalRangeUnitTest(){
		TestScheduler testScheduler = new TestScheduler();
		TestObserver testObserver =
			Observable.intervalRange(1, 5, 100L, 500L, TimeUnit.MILLISECONDS, testScheduler)
				  .map(data -> data * 100)
				  .doOnNext(this::printTime)
				  .test();

		testScheduler.advanceTimeTo(100L, TimeUnit.MILLISECONDS);
		testObserver.assertValue(100L);

		testScheduler.advanceTimeTo(100L + (500L * 3), TimeUnit.MILLISECONDS);
		testObserver.assertValues(100L, 200L, 300L, 400L);

		testScheduler.advanceTimeTo(100L + (500L * 4), TimeUnit.MILLISECONDS);
		testObserver.assertComplete();
		testObserver.assertNoErrors();
	}

	@Test
	void makeIntervalRangeUsingIntervalTest() throws InterruptedException {
		Observable.interval(500L, TimeUnit.MILLISECONDS)
				  .take(5)
				  .map(data -> (data+1) * 100) // 0부터 시작하기 때문에 +1을 시켜줘야한다.
				  .doOnNext(this::printTime)
				  .subscribe();
		Thread.sleep(3000L);

//		[RxComputationThreadPool-1] time = 617, data = 100
//		[RxComputationThreadPool-1] time = 1117, data = 200
//		[RxComputationThreadPool-1] time = 1621, data = 300
//		[RxComputationThreadPool-1] time = 2121, data = 400
//		[RxComputationThreadPool-1] time = 2621, data = 500
	}

	@Test
	void makeIntervalRangeUsingRangeTest() throws InterruptedException {
		Observable.range(1, 5)
				  .map(data -> data * 100)
				  .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS),
						   (val1, val2) -> val1)
				  .doOnNext(this::printTime)
				  .subscribe();
		Thread.sleep(1000L);

//		[RxComputationThreadPool-1] time = 205, data = 100
//		[RxComputationThreadPool-1] time = 305, data = 200
//		[RxComputationThreadPool-1] time = 404, data = 300
//		[RxComputationThreadPool-1] time = 505, data = 400
//		[RxComputationThreadPool-1] time = 604, data = 500
	}
}
