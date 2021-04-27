import io.reactivex.Single;
import org.junit.jupiter.api.Test;

public class SingleTest {

	@Test
	void justTest(){
		Single.just("hi")
			  .doOnSuccess(System.out::println)
			  .subscribe();
	}
}
