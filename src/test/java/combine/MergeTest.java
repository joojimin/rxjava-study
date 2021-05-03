package combine;

import io.reactivex.Observable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

public class MergeTest {

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
	void mergeTest() throws InterruptedException {
		String[] data1 = {"1", "3"};
		String[] data2 = {"2", "4", "6"};

		Observable<String> source1 = Observable.interval(0L, 100L, TimeUnit.MILLISECONDS)
			.map(Long::intValue)
			.map(idx -> data1[idx])
			.take(data1.length);

		Observable<String> source2 = Observable.interval(50L, TimeUnit.MILLISECONDS)
			.map(Long::intValue)
			.map(idx -> data2[idx])
			.take(data2.length);

		Observable.merge(source1, source2, source1, source1) // 4개까지는 merge
				  .doOnNext(this::printTime)
				  .subscribe();

		Thread.sleep(1000L);
	}

	@Test
	void mergeArrayTest() throws InterruptedException {
		String[] data1 = {"1", "3"};
		String[] data2 = {"2", "4", "6"};

		Observable<String> source1 = Observable.interval(0L, 100L, TimeUnit.MILLISECONDS)
											   .map(Long::intValue)
											   .map(idx -> data1[idx])
											   .take(data1.length);

		Observable<String> source2 = Observable.interval(50L, TimeUnit.MILLISECONDS)
											   .map(Long::intValue)
											   .map(idx -> data2[idx])
											   .take(data2.length);

		Observable.mergeArray(source1, source2, source1, source1, source2, source2)
				  .doOnNext(this::printTime)
				  .subscribe();

		Thread.sleep(1000L);
	}
}
