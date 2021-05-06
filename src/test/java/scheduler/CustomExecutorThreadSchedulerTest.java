package scheduler;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CustomExecutorThreadSchedulerTest extends PrintThread {

	@Test
	void executorThreadTest() throws InterruptedException {
		final int THREAD_NUM = 10;

		String[] data = {"1", "3", "5"};
		Observable<String> source = Observable.fromArray(data);
		Executor executor = Executors.newFixedThreadPool(THREAD_NUM);

		source.subscribeOn(Schedulers.from(executor))
			  .doOnNext(this::printTime)
			  .subscribe();

		source.subscribeOn(Schedulers.from(executor))
			  .doOnNext(this::printTime)
			  .subscribe();

		Thread.sleep(1000L);
	}


	@Test
	void executorSingleThreadTest() throws InterruptedException {
		String[] data = {"1", "3", "5"};
		Observable<String> source = Observable.fromArray(data);
		Executor executor = Executors.newSingleThreadExecutor();

		source.subscribeOn(Schedulers.from(executor))
			  .doOnNext(this::printTime)
			  .subscribe();

		source.subscribeOn(Schedulers.from(executor))
			  .doOnNext(this::printTime)
			  .subscribe();

		Thread.sleep(1000L);
	}

}
