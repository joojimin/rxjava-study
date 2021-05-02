package createmethod;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimerTest {

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
	void timerTest() throws InterruptedException {
		Observable.timer(500L, TimeUnit.MILLISECONDS)
				  .map(notUsed -> new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()))
				  .doOnNext(this::printTime)
				  .subscribe();
		Thread.sleep(1000L);
	}

	@Test
	void timerUnitTest(){
		TestScheduler testScheduler = new TestScheduler();
		TestObserver testObserver = Observable.timer(500L, TimeUnit.MILLISECONDS, testScheduler)
			.map(notUsed -> new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date())) // 실제 notUsed는 0값을 가지는 value지만 여기서는 사용안함
			.doOnNext(this::printTime)
			.test();

		testScheduler.advanceTimeBy(500L, TimeUnit.MILLISECONDS);
		testObserver.assertComplete();
		testObserver.assertNoErrors();
	}
}
