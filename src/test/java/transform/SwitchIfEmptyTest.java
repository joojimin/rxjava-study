package transform;

import io.reactivex.Observable;
import org.junit.jupiter.api.Test;
import scheduler.PrintThread;

class SwitchIfEmptyTest extends PrintThread {

	@Test
	void switchIfEmptyTest(){
		String[] args = {"1", "3", "5", "7"};

		Observable.fromArray(args)
				  .map(Integer::parseInt)
				  .filter(num -> (num % 2) == 0)
				  .switchIfEmpty(Observable.error(new RuntimeException("Not found")))
				  .doOnError(t -> this.printTime(t))
				  .onErrorReturnItem(-1)
				  .doOnNext(this::printTime)
				  .subscribe();

	}

	@Test
	void switchIfEmptyTestWithOutEmpty(){
		String[] args = {"1", "2", "5", "7", "8"};

		Observable.fromArray(args)
				  .map(Integer::parseInt)
				  .filter(num -> (num % 2) == 0)
				  .switchIfEmpty(Observable.error(new RuntimeException("Not found")))
				  .doOnError(t -> this.printTime(t))
				  .onErrorReturnItem(-1)
				  .doOnNext(this::printTime)
				  .subscribe();
	}


	@Test
	void switchIfEmptyTestDirectReturn(){
		String[] args = {"1", "3", "5", "7"};

		Observable.fromArray(args)
				  .map(Integer::parseInt)
				  .filter(num -> (num % 2) == 0)
				  .switchIfEmpty(Observable.just(-1))
				  .doOnNext(this::printTime)
				  .subscribe();
	}
}
