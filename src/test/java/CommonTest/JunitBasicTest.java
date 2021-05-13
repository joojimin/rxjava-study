package CommonTest;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import scheduler.PrintThread;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JunitBasicTest extends PrintThread {

	private String getShape(final String data){
		if (data.endsWith("-R")) {
			return "RECTANGLE";
		} else if (data.endsWith("-T")) {
			return "TRIANGLE";
		}
		return "BALL";
	}

	@DisplayName("test observable")
	@Test
	void testObservable(){
		String[] datas = {"1", "2-R", "3-T"};
		Observable<String> source = Observable.fromArray(datas)
											  .map(this::getShape);

		String[] expected = {"BALL", "RECTANGLE", "TRIANGLE"};
		List<String> actual = new ArrayList<>();
		source.doOnNext(this::printTime)
			  .subscribe(actual::add);

		assertEquals(Arrays.asList(expected), actual);
	}

	@DisplayName("#1: using TestObserver for Shap.getShape()")
	@Test
	void testUsingTestObserver(){
		String[] datas = {"1", "2-R", "3-T"};
		Observable<String> source = Observable.fromArray(datas)
											  .map(this::getShape);

		String[] expected = {"BALL", "RECTANGLE", "TRIANGLE"};
		source.test().assertResult(expected);
	}

	@DisplayName("assertFailure() example")
	@Test
	void testFailureExample(){
		String[] datas = {"100", "200", "%300", "400"};

		Observable<Integer> source = Observable.fromArray(datas)
											   .map(Integer::parseInt);

		source.test()
			  .assertFailure(NumberFormatException.class, 100, 200);
	}

	@DisplayName("assertFailureAndMessage() example")
	@Test
	void testFailureAndMessageExample(){
		String[] datas = {"100", "200", "%300", "400"};

		Observable<Integer> source = Observable.fromArray(datas)
											   .map(Integer::parseInt);

		source.test()
			  .assertFailureAndMessage(NumberFormatException.class, "For input string: \"%300\"", 100, 200); // message가 equal인듯,, contain이 아니다.
	}

	@DisplayName("assertComplete() example")
	@Test
	void assertComplete(){
		Observable<String> source = Observable.create((ObservableEmitter<String> emiiter) -> {
			emiiter.onNext("Hello RxJava");
			emiiter.onComplete();
		});

		source.test().assertComplete();
	}
}
