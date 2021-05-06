package scheduler;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import org.junit.jupiter.api.Test;

class ThreadExampleTest extends PrintThread{


	@Test
	void flipExampleTest() throws InterruptedException {
		String[] objs = {"1-S", "2-T", "3-P"};
		Observable<String> source =
			Observable.fromArray(objs)
					  .doOnNext(this::printTime) // 데이터가 발행되면 찍힘
					  .subscribeOn(Schedulers.newThread()) // 구독할때 스케줄러 정책
//					  .observeOn(Schedulers.newThread()) // 발행할때 스케줄러 정책
					  .map(data -> "flip " + data);

		source.subscribe(this::printTime); // 데이터를 구독하면서 찍힘
		Thread.sleep(1000L);

		// subscribeOn은 subscribe를 하는 순간 정해지는 스케줄러. 즉 모든 데이터흐름에서 연관
		// observeOn은 데이터흐름중 선언된 이후 순간의 스케줄러 정책을 결정
	}
}
