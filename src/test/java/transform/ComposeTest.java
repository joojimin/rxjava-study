package transform;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import scheduler.PrintThread;

class ComposeTest extends PrintThread {

	String[] datas;

	@BeforeEach
	void setUp(){
		datas = new String[] {"2", "3", "5", "7", "11", "13"};
	}

	@Test
	void commonObservableTest(){
		Observable.fromArray(datas)
				  .map(Integer::parseInt)
				  .doOnNext(this::printTime)
				  .doOnComplete(()-> this.printTime("onComplete"))
				  .subscribe();
	}

	@Test
	void flatMapTest(){
		Observable.fromArray(datas)
				  .map(Integer::parseInt)
				  .doOnNext(this::printTime)
				  .flatMap(data -> Observable.just(data*100))
				  .doOnNext(this::printTime)
				  .doOnComplete(() -> this.printTime("onComplete"))
				  .subscribe();
	}

	@Test
	void composeTest(){
		Observable.fromArray(datas)
				  .map(Integer::parseInt)
				  .doOnNext(this::printTime)
				  .compose(observable -> observable.map(data -> data*100))
				  .doOnNext(this::printTime)
				  .doOnComplete(() -> this.printTime("onComplete"))
				  .subscribe();
	}

	@Test
	void schedulerSubscribeOnAndObservableOnTest() throws InterruptedException {
		Observable.fromArray(datas)
				  .map(Integer::parseInt)
				  .doOnNext(this::printTime)
				  .subscribeOn(Schedulers.computation()) // upstream의 영향
				  .observeOn(Schedulers.io())
				  .doOnNext(this::printTime)
				  .doOnComplete(() -> this.printTime("onComplete"))
				  .subscribe();
		Thread.sleep(1000L);
	}

	@Test
	void schedulerComposeTest() throws InterruptedException {
		Observable.fromArray(datas)
				  .map(Integer::parseInt)
				  .doOnNext(this::printTime)
				  .compose(observable -> observable.subscribeOn(Schedulers.computation())
												   .observeOn(Schedulers.io()))
				  .doOnNext(this::printTime)
				  .doOnComplete(() -> this.printTime("onComplete"))
				  .subscribe();
		Thread.sleep(1000L);
	}
}
