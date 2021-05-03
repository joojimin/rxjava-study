package combine;

import io.reactivex.Observable;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

class ConcatTest {

	private void print(Object data){
		String threadName = Thread.currentThread().getName();
		System.out.println("[" + threadName + "]"
						   + ", data = " + data);
	}

	@Test
	void concatTest() throws InterruptedException {
		String[] data1 = {"1", "3", "5"};
		String[] data2 = {"2", "4", "6"};

		Observable<String> source1 = Observable.fromArray(data1)
			.doOnComplete(() -> print("source1 onComplete"));

		Observable<String> source2 = Observable.interval(100L, TimeUnit.MILLISECONDS)
			.map(Long::intValue)
			.map(idx -> data2[idx])
			.take(data2.length)
			.doOnComplete(() -> print("source2 onComplete"));

		Observable.concat(source1, source2, source1, source2)
				  .doOnNext(this::print)
				  .doOnComplete(() -> print("concat onComplete"))
				  .subscribe();

		Thread.sleep(1000L);
	}


	@Test
	void concatArrayTest() throws InterruptedException {
		String[] data1 = {"1", "3", "5"};
		String[] data2 = {"2", "4", "6"};

		Observable<String> source1 = Observable.fromArray(data1)
											   .doOnComplete(() -> print("source1 onComplete"));

		Observable<String> source2 = Observable.interval(100L, TimeUnit.MILLISECONDS)
											   .map(Long::intValue)
											   .map(idx -> data2[idx])
											   .take(data2.length)
											   .doOnComplete(() -> print("source2 onComplete"));

		Observable.concatArray(source1, source2, source1, source2, source1, source2)
				  .doOnNext(this::print)
				  .doOnComplete(() -> print("concat onComplete"))
				  .subscribe();

		Thread.sleep(1000L);
	}
}
