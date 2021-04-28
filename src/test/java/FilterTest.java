import io.reactivex.Observable;
import org.junit.jupiter.api.Test;

public class FilterTest {

	@Test
	void filterTest(){
		String[] objs = {"1 CIRCLE",
						 "2 DIAMOND",
						 "3 TRIANGLE",
						 "4 DIAMOND",
						 "5 CIRCLE",
						 "6 HEXAGON"};

		Observable.fromArray(objs)
				  .filter(value -> value.endsWith("CIRCLE"))
				  .doOnNext(System.out::println)
				  .subscribe();
	}

	@Test
	void filterTest2(){
		Integer[] data = {100, 34, 27, 99 ,50};

		Observable.fromArray(data)
				  .filter(num -> num % 2 == 0)
				  .doOnNext(System.out::println)
				  .subscribe();
	}

	@Test
	void firstTest(){
		Integer[] data = {100, 34, 27, 99 ,50};

		// 첫번째 값을 테스트함, 없으면 default value
		Observable.fromArray(data)
				  .first(-1)
				  .doOnSuccess(System.out::println)
				  .subscribe();
	}


}
