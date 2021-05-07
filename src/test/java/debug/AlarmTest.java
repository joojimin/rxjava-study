package debug;

import io.reactivex.Observable;
import org.junit.jupiter.api.Test;
import scheduler.PrintThread;

class AlarmTest extends PrintThread {

	@Test
	void doOnExampleTest(){
		String[] orgs = {"1", "3", "5"};
		Observable<String> source = Observable.fromArray(orgs);

		source.doOnNext(this::printTime)
			  .doOnComplete(()-> printTime("onComplete"))
			  .doOnError(this::printTime)
			  .subscribe();
	}

	@Test
	void doOnExampleWithErrorTest(){
		Integer[] divider = {10, 5, 0};

		Observable.fromArray(divider)
				  .map(div -> 1000/div)
				  .doOnNext(this::printTime)
				  .doOnComplete(() -> printTime("onComplete")) // onError로 잡히기떄문에 동작안됨 ( 데이터 발행이 완료안됨 )
				  .doOnError(this::printTime) // io.reactivex.exceptions.OnErrorNotImplementedException
				  .subscribe();
	}
}
