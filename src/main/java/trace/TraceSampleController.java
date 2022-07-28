package trace;

import com.google.api.gax.rpc.NotFoundException;
import com.google.cloud.ServiceOptions;
import com.google.cloud.trace.v1.TraceServiceClient;
import com.google.devtools.cloudtrace.v1.GetTraceRequest;
import com.google.devtools.cloudtrace.v1.PatchTracesRequest;
import com.google.devtools.cloudtrace.v1.Trace;
import com.google.devtools.cloudtrace.v1.TraceSpan;
import com.google.devtools.cloudtrace.v1.Traces;
import com.google.protobuf.Timestamp;
import java.io.IOException;
import java.time.Instant;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TraceSampleController {
        
    @GetMapping("/")
    public String hello() throws IOException, InterruptedException{
        String projectId = ServiceOptions.getDefaultProjectId();
        TraceServiceClient traceServiceClient = TraceServiceClient.create();

        // Create a trace in the current project.
        String traceId = UUID.randomUUID().toString().replaceAll("-", "");
        PatchTracesRequest createRequest = createPatchTraceRequest(traceId, projectId);
        traceServiceClient.patchTraces(createRequest);

        // Wait for the trace to be populated in Cloud Trace.
        System.out.println("Wait some time for the Trace to be populated.");
        Thread.sleep(15000);

        try {
            // This checks Cloud trace for the new trace that was just created.
            GetTraceRequest getTraceRequest =
                GetTraceRequest.newBuilder().setProjectId(projectId).setTraceId(traceId).build();

            Trace trace = traceServiceClient.getTrace(getTraceRequest);

            System.out.println("Retrieved trace: " + trace.getTraceId());
            System.out.println("It has the following spans: ");
            for (TraceSpan span : trace.getSpansList()) {
                System.out.println("Span: " + span.getName());
            }
        } catch (NotFoundException e) {
            System.out.println(
                "We didn't find the trace: "
                    + traceId
                    + ". "
                    + "This is usually because we did not wait long enough. "
                    + "Please check https://console.cloud.google.com/traces/traces to "
                    + "find your trace in the traces viewer.");
        }

        return "OK";
    }

    private static PatchTracesRequest createPatchTraceRequest(String traceId, String projectId) {
        long currentTime = Instant.now().toEpochMilli() / 1000;
    
        Trace trace =
            Trace.newBuilder()
                .setProjectId(projectId)
                .setTraceId(traceId)
                .addSpans(
                    TraceSpan.newBuilder()
                        .setSpanId(1)
                        .setName("nativeimage-trace-sample-test")
                        .setStartTime(Timestamp.newBuilder().setSeconds(currentTime - 5))
                        .setEndTime(Timestamp.newBuilder().setSeconds(currentTime)))
                .build();
    
        PatchTracesRequest request =
            PatchTracesRequest.newBuilder()
                .setProjectId(projectId)
                .setTraces(Traces.newBuilder().addTraces(trace))
                .build();
    
        return request;
    }    
}
