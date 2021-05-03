package create;

import io.reactivex.Observable;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

class repeatTest {

	private void print(Object data){
		String threadName = Thread.currentThread().getName();
		System.out.println("[" + threadName + "]"
						   + ", data = " + data);
	}

	@Test
	void repeatTest(){
		String[] balls = {"1", "3", "5"};
		Observable<String> observable =
			Observable.fromArray(balls)
				  .repeat(3); // 인자를 안주면 영원히 반복

		observable.subscribe(data -> print("Subscribe #1 = " + data));
	}

	@Test
	void heartBeatTest() throws InterruptedException {
		Observable.timer(1l, TimeUnit.SECONDS)
				  .doOnNext(this::print)
				  .repeat(5) // 동작이 한번 끝난 다음에 다시 구독하는 방식으로 동작한다.
				  .subscribe();
		Thread.sleep(5000L);
	}

	@Test
	void heartBeatTest2() throws InterruptedException {
		Observable.interval(1L, TimeUnit.SECONDS)
				  .doOnNext(this::print)
				  .take(5)
				  .subscribe();
		Thread.sleep(5000L);
	}
}
