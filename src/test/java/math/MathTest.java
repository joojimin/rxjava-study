package math;

import hu.akarnokd.rxjava2.math.MathFlowable;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import org.junit.jupiter.api.Test;

class MathTest {

	private void print(Object data){
		String threadName = Thread.currentThread().getName();
		System.out.println("[" + threadName + "]"
						   + ", data = " + data);
	}

	@Test
	void mathTest(){
		Integer[] data = {1,2,3,4};

		// 1. count
		Observable.fromArray(data)
				  .count()
				  .doOnSuccess(this::print)
				  .subscribe();

		// 2. max() & min()
		Flowable.fromArray(data)
				.to(MathFlowable::max) // 다른 함수로 변환
				.doOnNext(this::print)
				.subscribe();

		Flowable.fromArray(data)
				.to(MathFlowable::min)
				.doOnNext(this::print)
				.subscribe();

		// 3. sum() & average
		Flowable.fromArray(data)
				.to(MathFlowable::sumInt)
				.doOnNext(this::print)
				.subscribe();

		Observable.fromArray(data)
				  .toFlowable(BackpressureStrategy.BUFFER) // Observable -> Flowable
				  .to(MathFlowable::averageDouble)
				  .doOnNext(this::print)
				  .subscribe();
	}
}
