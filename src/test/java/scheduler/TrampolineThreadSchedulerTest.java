package scheduler;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import org.junit.jupiter.api.Test;

public class TrampolineThreadSchedulerTest extends PrintThread{

	@Test
	void trampolineThreadTest() throws InterruptedException {
		String[] orgs = {"1", "3", "5"};
		Observable<String> source = Observable.fromArray(orgs);

		// 구독 #1
		source.subscribeOn(Schedulers.trampoline()) // subscribeOn의 호출 위치는 상관없다.
			  .map(data -> "<<" + data + ">>")
			  .doOnNext(this::printTime)
			  .subscribe();

		// 구독 #2
		source.subscribeOn(Schedulers.trampoline())
			  .map(data -> "##" + data + "##")
			  .doOnNext(this::printTime)
			  .subscribe();

		Thread.sleep(1000L);
	}
}
