package createmethod;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.TestScheduler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

class IntervalTest {

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
	@DisplayName("interval(long period, TimeUnit unit) test")
	void intervalTest() throws InterruptedException {
		Observable.interval(100L, TimeUnit.MILLISECONDS) // 처음 데이터 발행은 period가 지난후
				  .doOnNext(System.out::println) // data 출력
				  .map(data -> (data+1) * 100) // data = 0부터 발행 ( 초기값이 0이니까 곱하기위해 + 1)
				  .take(5)
				  .doOnNext(this::printTime)
				  .subscribe();
		Thread.sleep(1000l); // 기본 원형은 별도의 계산 스케줄러에 의해 실행되기 때문에 sleep을 줘서 테스트

//		0
//		[RxComputationThreadPool-1] time = 175, data = 100
//		1
//		[RxComputationThreadPool-1] time = 269, data = 200
//		2
//		[RxComputationThreadPool-1] time = 371, data = 300
//		3
//		[RxComputationThreadPool-1] time = 468, data = 400
//		4
//		[RxComputationThreadPool-1] time = 569, data = 500
	}

	@Test
	@DisplayName("interval(long initialDelay, long period, TimeUnit unit) test")
	void intervalTestWithInitialDelay() throws InterruptedException {
		Observable.interval(500l, 100l, TimeUnit.MILLISECONDS) // 초기시간이 지나고 period로 발행
				  .map(data -> data+100)
				  .take(5)
				  .doOnNext(this::printTime)
				  .subscribe();
		Thread.sleep(2000l); // 기본 원형은 별도의 계산 스케줄러에 의해 실행되기 때문에 sleep을 줘서 테스트

//		[RxComputationThreadPool-1] time = 578, data = 100
//		[RxComputationThreadPool-1] time = 677, data = 101
//		[RxComputationThreadPool-1] time = 776, data = 102
//		[RxComputationThreadPool-1] time = 876, data = 103
//		[RxComputationThreadPool-1] time = 978, data = 104
	}

	@Test
	@DisplayName("intervalTest(long period, TimeUnit unit, Scheduler schduler) test")
	void intervalTestWithMyScheduler() throws InterruptedException {
		Observable.interval(100L, TimeUnit.MILLISECONDS, Schedulers.single())
				  .map(data -> (data+1) * 100)
				  .take(5)
				  .doOnNext(this::printTime)
				  .subscribe();
		Thread.sleep(1000l);

//		[RxSingleScheduler-1] time = 192, data = 100
//		[RxSingleScheduler-1] time = 292, data = 200
//		[RxSingleScheduler-1] time = 394, data = 300
//		[RxSingleScheduler-1] time = 490, data = 400
//		[RxSingleScheduler-1] time = 590, data = 500
	}


	@Test
	@DisplayName("interval unit test")
	void intervalUnitTest(){
		Observable.interval(100L, TimeUnit.MILLISECONDS) // period 시간이 늘어나거나 take하는 갯수가 많아지면 오래기달려야할 수도 있다.
				  .map(data -> (data + 1) * 100)
				  .take(5)
				  .doOnNext(this::printTime)
				  .test()
				  .awaitDone(500l, TimeUnit.MILLISECONDS) // test() 함수가 실행되는 스레드에서 onComplete() 함수를 호출할 때까지 기다려준다.
				  .assertResult(100l, 200l, 300l, 400l, 500l);

//		awaitDone하는 시간보다 interval이 더 길어진다면 Error
//		java.lang.AssertionError: Not completed (latch = 1, values = 5, errors = 0, completions = 0, timeout!, disposed!)
	}

	@Test
	@DisplayName("interval unit test 2")
	void intervalUnitTest2(){
		TestScheduler testScheduler = new TestScheduler();
		TestObserver<Long> testObserver = Observable
			.interval(100L, TimeUnit.MILLISECONDS, testScheduler)
			.map(data -> (data + 1) * 100)
			.take(100)
			.doOnNext(this::printTime)
			.test();

		testScheduler.advanceTimeBy(0L, TimeUnit.MILLISECONDS);
		testObserver.assertEmpty();

		testScheduler.advanceTimeBy(100L, TimeUnit.MILLISECONDS);
		testObserver.assertValue(100L);

		testScheduler.advanceTimeBy(100L, TimeUnit.MILLISECONDS);
		testObserver.assertValues(100L, 200L);

		testScheduler.advanceTimeBy(10000L, TimeUnit.MILLISECONDS);
		testObserver.assertComplete();
		testObserver.assertNoErrors();


//		[main] time = 65, data = 100
//		[main] time = 88, data = 200
//		[main] time = 88, data = 300
//		[main] time = 88, data = 400
//		[main] time = 88, data = 500
//		......
	}
}
