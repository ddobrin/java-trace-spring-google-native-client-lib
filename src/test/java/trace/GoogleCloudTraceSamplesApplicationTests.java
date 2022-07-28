package trace;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GoogleCloudTraceSamplesApplicationTests {
	// @Autowired
	// TestRestTemplate restTemplate;

	private ByteArrayOutputStream bout;
	
	@Test
	void contextLoads() {
	}

	@Before
	public void setUp() throws Exception {
	  bout = new ByteArrayOutputStream();
	  System.setOut(new PrintStream(bout));
	}
  
	// @Test
	// public void testRunSampleApplication() throws Exception {
	// 	GoogleCloudTraceSamplesApplication.main(new String[] {});
	//   	String output = bout.toString();
	//   	assertThat(output).contains("Retrieved trace:");
	//   	assertThat(output).contains("Span: nativeimage-trace-sample-test");
	// }	

	// @Test
	// public void testTrace(){
	// 	assertEquals("OK", restTemplate.getForObject("/", String.class));
	// }
}
