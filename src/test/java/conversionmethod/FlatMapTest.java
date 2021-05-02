package conversionmethod;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import org.junit.jupiter.api.Test;


public class FlatMapTest {


	@Test
	void flatMapSimpleTest(){
		//	java.util.Function 패키지 Function 클래스를 이용하면 오류난다.
		Function<String, Observable<String>> getDoubleDiamonds =
			ball -> Observable.just(ball + "<>", ball + "<>");


		//	ball array중 발행된 데이터를 가지고 Function 함수를 거쳐
		//	Observable.just로 Observable<String>으로 변환
		//	이를 다시 평평하게 해주는 것이 flatMap
		String[] balls = {"1", "3", "5"};
		Observable.fromArray(balls)
				  .flatMap(getDoubleDiamonds)
				  .doOnNext(System.out::println)
				  .subscribe();

//		1<>
//		1<>
//		3<>
//		3<>
//		5<>
//		5<>
	}
}
