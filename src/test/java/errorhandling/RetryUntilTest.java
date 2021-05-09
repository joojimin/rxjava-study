package errorhandling;

import io.reactivex.Observable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import scheduler.PrintThread;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

class RetryUntilTest extends PrintThread {

	@Test
	@DisplayName("30초동안 반복 테스트")
	void retryUntilTest() throws InterruptedException {
		final long LIMIT_SECOND = 30l;
		LocalDateTime localDateTime = LocalDateTime.now();

		Observable<Long> source = Observable.interval(100L, TimeUnit.MILLISECONDS)
											  .retryUntil(() -> {
												  LocalDateTime limitTime = LocalDateTime.now();
												  if ((limitTime.getSecond() - localDateTime.getSecond()) > LIMIT_SECOND){
												  	return true;
												  }

												  Thread.sleep(1000L);
												  return false;
											  });

		source.doOnNext(this::printTime)
			  .subscribe();

		Thread.sleep(3000L);
	}
}
