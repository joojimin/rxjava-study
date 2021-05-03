package createmethod;

import io.reactivex.Observable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.Callable;

class deferTest {

	private Iterator<String> colors;

	private void print(Object data){
		String threadName = Thread.currentThread().getName();
		System.out.println("[" + threadName + "]"
						   + ", data = " + data);
	}

	@BeforeEach
	void setUp(){
		colors = Arrays.asList("1", "3", "5", "6").iterator();
	}

	@Test
	void deferTest(){
		Callable<Observable<String>> supplier = () -> getObservable(); // 각도형별로 발행
		Observable<String> source = Observable.defer(supplier);

		source.subscribe(val -> print("Subscriber #1:" + val));
		source.subscribe(val -> print("Subscriber #2:" + val));
		source.subscribe(val -> print("Subscriber #3:" + val));
		source.subscribe(val -> print("Subscriber #4:" + val));
		source.subscribe(val -> print("Subscriber #5:" + val)); // 데이터가 없으니 발행자체가 안됨
	}

	private Observable<String> getObservable(){
		if(colors.hasNext()){
			String color = colors.next();
			return Observable.just(
				color,
				color +"-R",
				color +"-P");
		}
		return Observable.empty();
	}
}
