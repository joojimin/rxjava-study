package scheduler;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import org.junit.jupiter.api.Test;

import java.io.File;

public class IOThreadSchedulerTest extends PrintThread{

	@Test
	void ioThreadTest() throws InterruptedException {
		String filePath = "./src/test/java/scheduler";
		File[] files = new File(filePath).listFiles();
		Observable<String> source = Observable.fromArray(files)
											  .filter(f -> !f.isDirectory())
											  .map(f -> f.getAbsolutePath())
											  .subscribeOn(Schedulers.io());
		source.doOnNext(this::printTime)
			  .subscribe();
		Thread.sleep(1000L);
	}
}
