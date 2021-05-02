package conversionmethod;

import io.reactivex.Observable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ReduceTest {

	@ParameterizedTest
	@ValueSource(ints = {
		1,2,3,4,5,6,7,8,9
	})
	@DisplayName("구구단 전체의 합을 출력하는 Reduce 연산자 테스트")
	void reduceTest(int value){
		Observable.just(value)
				  .flatMap(num -> Observable.range(1, 9),
						   (num, i) -> num * i)
				  .reduce((num1, num2) -> num1 + num2)
				  .doOnSuccess(System.out::println) // reduce와 doOnComplete는 안맞나..?
				  .subscribe();
	}
}
