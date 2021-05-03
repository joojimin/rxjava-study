package create;

import io.reactivex.Observable;
import org.junit.jupiter.api.Test;

public class RangeTest {

	private void print(Object data){
		String threadName = Thread.currentThread().getName();
		System.out.println("[" + threadName + "]"
						   + ", data = " + data);
	}

	@Test
	void rangeTest(){
		Observable.range(1, 10)
				  .filter(number -> number % 2 == 0)
				  .doOnNext(this::print)
				  .subscribe();
//		2
//		4
//		6
//		8
//		10
	}
}
