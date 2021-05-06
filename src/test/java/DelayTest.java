import io.reactivex.Observable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

class DelayTest {

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
	void delayTest() throws InterruptedException {
		String[] data = {"1", "7", "2", "3", "4"};
		Observable.timer(100L, TimeUnit.MILLISECONDS)
				  .flatMap(notUsed -> Observable.fromArray(data))
				  .doOnNext(this::printTime)
				  .subscribe();
		Thread.sleep(1000L);
	}

	@Test
	void withOutDelayTest() throws InterruptedException {
		String[] data = {"1", "7", "2", "3", "4"};
		Observable.fromArray(data)
				  .doOnNext(this::printTime)
				  .subscribe();
		Thread.sleep(1000L);
	}
}
