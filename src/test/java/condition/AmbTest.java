package condition;

import io.reactivex.Observable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

class AmbTest {

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
	void ambTest() throws InterruptedException {
		String[] data1 = {"1", "3", "5"};
		String[] data2 = {"2-R", "4-R"};

		List<Observable<String>> sources = Arrays.asList(Observable.fromArray(data1)
																   .doOnComplete(()->System.out.println("Observable #1 : onComplete")),
														 Observable.fromArray(data2)
																   .delay(100L, TimeUnit.MILLISECONDS)
																   .doOnComplete(()->System.out.println("Observable #2 : onComplete")));
		Observable.amb(sources)
				  .doOnNext(this::printTime)
				  .doOnComplete(()-> System.out.println("Result: onComplete"))
				  .subscribe();
		Thread.sleep(1000L);
	}
}
