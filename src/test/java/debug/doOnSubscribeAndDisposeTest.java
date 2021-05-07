package debug;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import org.junit.jupiter.api.Test;
import scheduler.PrintThread;

import java.util.concurrent.TimeUnit;

class doOnSubscribeAndDisposeTest extends PrintThread {

	@Test
	void doOnSubscribeAndDisposeTest() throws InterruptedException {
		String[] orgs = {"1", "3", "5", "2", "6"};
		Observable<String> source = Observable.fromArray(orgs)
											  .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), (data, notUsed)-> data)
											  .doOnNext(this::printTime)
											  .doOnSubscribe(d -> printTime("subscribe : " + d))
											  .doOnDispose(()-> printTime("dispose"));

		Disposable d = source.subscribe();
		Thread.sleep(200L);
		d.dispose(); // 구독해제
		Thread.sleep(300L);
	}

	@Test
	void doOnLifeCycleTest() throws InterruptedException {
		String[] orgs = {"1", "3", "5", "2", "6"};
		Observable<String> source = Observable.fromArray(orgs)
											  .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), (data, notUsed)-> data)
											  .doOnNext(this::printTime)
											  .doOnLifecycle(d->printTime("subscirbe :" + d),
															 ()->printTime("dispose"));

		Disposable d = source.subscribe();
		Thread.sleep(200L);
		d.dispose(); // 구독해제
		Thread.sleep(300L);
	}

}
