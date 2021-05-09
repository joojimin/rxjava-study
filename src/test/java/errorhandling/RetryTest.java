package errorhandling;

import io.reactivex.Observable;
import org.junit.jupiter.api.Test;
import scheduler.PrintThread;

class RetryTest extends PrintThread {

	@Test
	void retryTest(){
		String getUrl = "http://get";
		Observable<String> source = Observable.just(getUrl)
											  .map(data -> {
												  printTime("http get " + data);
												  return data;
											  })
											  .retry(5) // error가 나지않고 정상적으로 onComplete가 되면 retry하지 않음
											  .onErrorReturnItem("ERROR");

		source.subscribe(this::printTime);
	}


	@Test
	void retryWithDelayTest(){
		final int RETRY_MAX = 5;
		final long RETRY_DELAY = 1000L;

		String getUrl = "http://get";
		Observable<String> source = Observable.just(getUrl)
											  .map(data -> {
												  printTime("http get" + data);
												  int num = 100/0;
												  return data;
											  })
											  .retry((retryCnt, e) -> {
												  printTime("retryCnt " + retryCnt);
												  Thread.sleep(RETRY_DELAY);
												  return retryCnt < RETRY_MAX ? true : false;
											  }).onErrorReturnItem("ERROR");

		source.doOnNext(this::printTime)
			  .subscribe();
	}
}
