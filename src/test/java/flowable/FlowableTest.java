package flowable;

import io.reactivex.BackpressureOverflowStrategy;
import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import scheduler.PrintThread;

import java.util.concurrent.Flow;

class FlowableTest extends PrintThread {

	@Test
	@DisplayName("flowable test")
	void flowableTest(){
		Flowable.just("Hello world")
				.subscribe(new Consumer<String>() {
					@Override
					public void accept(String s) throws Exception {
						printTime(s);
					}
				});
	}

	@Test
	@DisplayName("backpressure test")
	void backpressureExampleTest(){
		PublishSubject<Integer> subject = PublishSubject.create();
		subject.observeOn(Schedulers.computation())
			   .doOnNext(data -> {
				   Thread.sleep(100L); // 생각했던 100L마다 emit이 안된다.. 엄청느리다.
				   this.printTime(data);
			   })
			   .doOnError(e -> {
				   this.printTime(e);
			   })
			   .subscribe();

		for(int i = 0; i < 50_000_000; ++i){
			subject.onNext(i);
		}
		subject.onComplete();;
	}

	@Test
	@DisplayName("backpressure test with buffer")
	void backpressureExampleWithBufferTest() throws InterruptedException {
		Flowable.range(1, 50_000_000)
				.onBackpressureBuffer(128, ()->{}, BackpressureOverflowStrategy.DROP_OLDEST)
				.observeOn(Schedulers.computation())
				.doOnError(e->this.printTime("ERROR: " + e))
				.subscribe(data -> {
					Thread.sleep(100L);
					this.printTime(data);
				});
		Thread.sleep(20000L);
	}

	@Test
	@DisplayName("backpressurelatest test")
	void backpressureLastestTest() throws InterruptedException {
		Flowable.range(1, 50_000_000)
				.onBackpressureLatest()
				.observeOn(Schedulers.computation())
				.doOnError(e->this.printTime("ERROR: " + e))
				.subscribe(data -> {
					Thread.sleep(100L);
					this.printTime(data);
				});
		Thread.sleep(20000L);
	}

}
