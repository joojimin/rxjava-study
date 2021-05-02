package createmethod;

import io.reactivex.Observable;
import org.junit.jupiter.api.Test;

class repeatTest {

	private void print(Object data){
		String threadName = Thread.currentThread().getName();
		System.out.println("[" + threadName + "]"
						   + ", data = " + data);
	}

	@Test
	void repeatTest(){
		String[] balls = {"1", "3", "5"};
		Observable.fromArray(balls)
				  .repeat(3) // 인자를 안주면 영원히 반복
				  .doOnNext(this::print)
				  .subscribe();
	}
}
