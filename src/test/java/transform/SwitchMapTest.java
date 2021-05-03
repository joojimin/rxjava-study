package transform;

import io.reactivex.Observable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

class SwitchMapTest {

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
	void switchMapTest() throws InterruptedException {
		String[] balls = {"1", "3", "5"};

		Observable<String> soruce = Observable.interval(100L, TimeUnit.MILLISECONDS)
		.map(Long::intValue)
		.map(idx -> balls[idx])
		.take(balls.length)
		.doOnNext(this::printTime)
		.switchMap(ball -> Observable.interval(50L, TimeUnit.MILLISECONDS)
				  .map(notUsed -> ball + "<>")
				  .take(2))
		.doOnNext(this::printTime);

		soruce.subscribe();
		Thread.sleep(2000L);
	}
}
