package transform;

import io.reactivex.Observable;
import org.junit.jupiter.api.Test;

public class ScanTest {

	private void print(Object data){
		String threadName = Thread.currentThread().getName();
		System.out.println("[" + threadName + "]"
						   + ", data = " + data);
	}

	@Test
	void scanTest(){
		String[] balls = {"1", "3", "5"};
		Observable.fromArray(balls)
				  .scan((ball1, ball2) -> ball2 + "(" + ball1 + ")")
				  .doOnNext(this::print)
				  .subscribe();

//		[main], data = 1
//		[main], data = 3(1)
//		[main], data = 5(3(1))
	}

	@Test
	void reduceTest(){
		String[] balls = {"1", "3", "5"};
		Observable.fromArray(balls)
				  .reduce((ball1, ball2) -> ball2 + "(" + ball1 + ")")
				  .doOnSuccess(this::print)
				  .subscribe();

//		[main], data = 5(3(1))
	}
}
