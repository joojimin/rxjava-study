package conversionmethod;

import io.reactivex.Observable;
import org.junit.jupiter.api.Test;

public class MapTest {


	@Test
	void mapSimpleTest(){
		String[] balls = {"1", "3", "5", "7"};
		Observable.fromArray(balls)
				  .map(data -> "prefix " + data)
				  .doOnNext(System.out::println)
				  .subscribe();

//		prefix 1
//		prefix 3
//		prefix 5
//		prefix 7
	}
}
