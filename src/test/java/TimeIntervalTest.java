import io.reactivex.Observable;
import io.reactivex.schedulers.Timed;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

class TimeIntervalTest {

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
	void timeIntervalTest() throws InterruptedException {
		String[] data = {"1", "3", "7"};

		Observable<Timed<String>> source = Observable.fromArray(data)
			.delay(item -> {
				Thread.sleep(100l);
				return Observable.just(item);
			})
			.timeInterval();

		source.doOnNext(this::printTime)
			  .subscribe();
		Thread.sleep(1000L);
	}

	private void doSomething() throws InterruptedException {
		Thread.sleep(new Random().nextInt(100));
	}
}
