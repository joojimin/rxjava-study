package transform;

import io.reactivex.Observable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

class ConcatMapTest {

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
	void concatMapTest() throws InterruptedException {
		String[] balls = {"1", "3", "5"};

		Observable<String> source = Observable.interval(100L, TimeUnit.MILLISECONDS)
			.map(Long::intValue)
			.map(idx -> balls[idx])
			.take(balls.length)
			.concatMap(ball -> Observable.interval(200L, TimeUnit.MILLISECONDS)
										 .map(notUsed -> ball + "<>")
										 .take(2));
		source.subscribe(this::printTime);
		Thread.sleep(2000L);
	}

	@Test
	void flatMapTest() throws InterruptedException {
		String[] balls = {"1", "3", "5"};

		Observable<String> source = Observable.interval(100L, TimeUnit.MILLISECONDS)
											  .map(Long::intValue)
											  .map(idx -> balls[idx])
											  .take(balls.length)
											  .flatMap(ball -> Observable.interval(200L, TimeUnit.MILLISECONDS)
																		   .map(notUsed -> ball + "<>")
																		   .take(2));
		source.subscribe(this::printTime);
		Thread.sleep(2000L);
	}
}
