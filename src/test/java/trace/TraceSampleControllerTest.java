package trace;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertEquals;

import java.io.PrintStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TraceSampleControllerTest {
    @Autowired
    private TraceSampleController controller;

    @Test
	public void contextLoads() throws Exception {
		assertThat(controller).isNotNull(); 
	}
  
	@Test
	public void testRunSampleApplication() throws Exception {

        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bout));
  
		controller.hello();
	  	String output = bout.toString();
	  	assertThat(output).contains("Retrieved trace:");
	  	assertThat(output).contains("Span: nativeimage-trace-sample-test");
	}	

	@Test
	public void testTrace() throws IOException, InterruptedException{
        assertEquals("OK", controller.hello());
	}    
}
