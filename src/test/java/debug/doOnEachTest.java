package debug;

import io.reactivex.Observable;
import org.junit.jupiter.api.Test;
import scheduler.PrintThread;

class doOnEachTest extends PrintThread {

	@Test
	void doOnEachTest(){
		String[] datas = {"ONE", "TWO", "THREE"};
		Observable<String> source = Observable.fromArray(datas);

		source.doOnEach(noti -> {
			if(noti.isOnNext()) printTime("onNext : " + noti.getValue());
			if(noti.isOnComplete()) printTime("onComplete");
			if(noti.isOnError()) printTime("onError : " + noti.getError().getMessage());
		}).subscribe();
	}
}
