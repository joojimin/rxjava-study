package errorHandling;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import org.junit.jupiter.api.Test;
import scheduler.PrintThread;

class OnErrorResumeNextTest extends PrintThread {


	@Test
	void onErrorResumeNextTest(){
		String[] salesData = {"100", "200", "A300"};

		Observable<Integer> onParseError = Observable.defer(()->{
			printTime("send email to administrator");
			return Observable.just(-1);
		}).subscribeOn(Schedulers.io());

		Observable<Integer> source = Observable.fromArray(salesData)
											   .map(Integer::parseInt)
											   .onErrorResumeNext(onParseError);

		source.subscribe(data -> {
			if(data < 0){
				printTime("Wrong Data found!");
				return;
			}

			printTime("Sales data : " + data);
		});
	}
}
