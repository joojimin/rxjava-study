package combine;

import io.reactivex.Observable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

class ZipTest {


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

	private void print(Object data){
		String threadName = Thread.currentThread().getName();
		System.out.println("[" + threadName + "]"
						   + ", data = " + data);
	}

	@Test
	@DisplayName("갯수가 같을때")
	void zipTest(){
		String[] shapes = {"BALL", "PENTAGON", "STAR"};
		String[] coloredTriangles = {"2-T", "6-T", "4-T"};

		Observable<String> source = Observable.zip(
			Observable.fromArray(shapes),
			Observable.fromArray(coloredTriangles),
			(firstObservable, secondObservable)-> firstObservable + " : " + secondObservable);

		source.subscribe(this::print);
	}

	@Test
	@DisplayName("갯수가 다를 경우")
	void zipTest2(){
		String[] shapes = {"BALL", "PENTAGON", "STAR", "TRIANGLE"};
		String[] coloredTriangles = {"2-T", "6-T", "4-T", "5-G", "6"};

		Observable<String> source = Observable.zip(
			Observable.fromArray(shapes),
			Observable.fromArray(coloredTriangles),
			(firstObservable, secondObservable)-> firstObservable + " : " + secondObservable)
			.doOnComplete(()->System.out.println("onComplete"));

		source.subscribe(this::print);
	}

	@Test
	void zipIntervalTest() throws InterruptedException {
		Observable.zip(
			Observable.just("RED", "GREEN", "BLUE"),
			Observable.interval(200l, TimeUnit.MILLISECONDS),
			(value, time) -> value)
			.doOnNext(this::printTime)
			.subscribe();

		Thread.sleep(1000L);
	}

	@Test
	void zipWithTest() throws InterruptedException {
		Observable.just("hi", "I", "am", "jimin", "joo")
				  .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), (a, b) -> a)
				  .doOnNext(this::printTime)
				  .subscribe();

		Thread.sleep(1000L);
	}
}
