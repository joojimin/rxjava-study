package scheduler;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import org.junit.jupiter.api.Test;

public class NewThreadSchedulerTest extends PrintThread{

	@Test
	void newThreadTest() throws InterruptedException {
		String[] orgs = {"1", "3", "5"};
		Observable.fromArray(orgs)
				  .doOnNext(this::printTime)
				  .map(data -> "<<" + data + ">>")
				  .subscribeOn(Schedulers.newThread())
				  .doOnNext(this::printTime)
				  .subscribe();
		Thread.sleep(500L);

		String[] orgs1 = {"7", "9", "11"};
		Observable.fromArray(orgs1)
				  .doOnNext(this::printTime)
				  .map(data -> "##" + data + "##")
				  .subscribeOn(Schedulers.newThread())
				  .doOnNext(this::printTime)
				  .subscribe();
		Thread.sleep(500L);
	}
}
