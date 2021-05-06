package scheduler;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

public class ComputationThreadSchedulerTest extends PrintThread{

	@Test
	void computationThreadTest() throws InterruptedException {
		String[] orgs = {"1", "3", "5"};
		Observable<String> source = Observable.fromArray(orgs)
											  .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), (data,notUsed)-> data);

		// 구독 #1
		source.map(item -> "<<" + item + ">>")
			  .subscribeOn(Schedulers.computation())
			  .doOnNext(this::printTime)
			  .subscribe();
		Thread.sleep(1000L); // 주석처리하면 구독자 #1과 구독자 #2가 거의 동시에 이루어지기 때문에 RxJava 내부에서 동일한 스레드에 작업을 할당할 수도 있다.


		// 구독 #2
		source.map(item -> "##" + item + "##")
			  .subscribeOn(Schedulers.computation())
			  .doOnNext(this::printTime)
			  .subscribe();

		Thread.sleep(1000L);
	}
}
