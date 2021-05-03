package condition;

import io.reactivex.Observable;
import io.reactivex.Single;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

class AllTest {

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
	void allTest() throws InterruptedException {
		String[] responses = {"200", "200", "200", "403", "200", "200", "200"};

		Single<Boolean> source = Observable.fromArray(responses)
										   .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS),
													(val, notUsed)->val)
										   .doOnNext(this::printTime)
										   .all("200"::equals);

		source.subscribe();
		Thread.sleep(1000L);
	}
}
