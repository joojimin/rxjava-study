package combinemethod;

import io.reactivex.Observable;
import io.reactivex.observables.ConnectableObservable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

class CombineLatestTest {

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
	void combineLatestTest() throws InterruptedException {
		String[] data1 = {"6", "7", "4", "2"};
		String[] data2 = {"DIAMOND", "STAR", "PENTAGON"};

		Observable<String> source = Observable.combineLatest(
			Observable.fromArray(data1).zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS),
												(value1, notUsed) -> value1),
			Observable.fromArray(data2).zipWith(Observable.interval(150L, 200L, TimeUnit.MILLISECONDS),
												(value2, notUsed) -> value2),
			(v1, v2) -> v1 + " : " + v2);

		source.subscribe(this::printTime);
		Thread.sleep(1000L);
	}

	@Test
	void reactiveOperationSumTest(){
		String[] dataArray = {"a:100", "b:2020", "a:300"};
		ConnectableObservable<String> source = Observable.fromArray(dataArray).publish();

		Observable<Integer> a = source
			.filter(str-> str.startsWith("a:"))
			.map(str -> str.replace("a:", ""))
			.map(Integer::parseInt)
			.doOnNext(data -> System.out.println("a : " + data));

		Observable<Integer> b = source
			.filter(str -> str.startsWith("b:"))
			.map(str -> str.replace("b:", ""))
			.map(Integer::parseInt)
			.doOnNext(data -> System.out.println("b : " + data));

		Observable.combineLatest(a, b, (x, y) -> x+y)
				  .subscribe(this::printTime);

		source.connect();
	}
}
