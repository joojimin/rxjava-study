import io.reactivex.Observable;
import io.reactivex.functions.Function;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class MultiplicationTableTest {

	@ParameterizedTest
	@ValueSource(ints = {
		1, 2, 3, 4, 5, 6, 7, 8, 9
	})
	@DisplayName("일반적인 java 소스를 이용한 구구단 게임")
	void multiplicationTableTestUsingCommonJava(int value){
		for(int row = 1; row <= 9; ++row){
			System.out.println(value + " * " + row + " = " + (value * row));
		}
	}


	@ParameterizedTest
	@ValueSource(ints = {
		1, 2, 3, 4, 5, 6, 7, 8, 9
	})
	@DisplayName("RxJava 소스를 이용한 구구단 게임")
	void multiplicationTableTestUsingRxJava(int inputValue){
//		// 1~9까지 곱셈연산을 하는 연산자 정의
//		Function<Integer, Observable<Integer>> function
//			= number -> Observable.range(1,9).map(value -> value * number);
//
//		// flatMap을 통한 출력
//		Observable.just(inputValue)
//				  .flatMap(function)
//				  .doOnNext(System.out::println)
//				  .subscribe();

//		// 한번에
//		Observable.range(1, 9)
//				  .map(value -> value * inputValue)
//				  .doOnNext(System.out::println)
//				  .subscribe();

		Observable.just(inputValue)
				  .flatMap(gugu -> Observable.range(1, 9),
						   (gugu, i) -> gugu + " * " + i + " = " + gugu*i)
				  .doOnNext(System.out::println)
				  .subscribe();
	}
}
