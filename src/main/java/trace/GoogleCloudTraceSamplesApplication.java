package trace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.nativex.hint.InitializationHint;
// import org.springframework.nativex.hint.InitializationTime;
// import org.springframework.nativex.hint.NativeHint;

// @NativeHint(
// 		initialization = @InitializationHint(types = {
// 			io.grpc.netty.shaded.io.netty.util.internal.logging.Slf4JLoggerFactory.class
// 		}, initTime = InitializationTime.RUN))
@SpringBootApplication
public class GoogleCloudTraceSamplesApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoogleCloudTraceSamplesApplication.class, args);
	}

}
