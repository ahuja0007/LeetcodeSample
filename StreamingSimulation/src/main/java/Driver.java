import stageTask.Base64Encode;
import stageTask.FilePersistence;
import stageTask.ReverseString;
import streamPipelineInit.SimulatePipeline;

import java.util.Arrays;

public class Driver {
    public static void main(String[] args) throws Exception {

        String input = "/path/to/input/file";
        String outPutFileName = "/path/to/output/file";

        SimulatePipeline simulatePipeline = SimulatePipeline.builder()
                .pipelineStages(Arrays.asList(new ReverseString(), new Base64Encode(),
                        FilePersistence.builder().outputFileName(outPutFileName).build())).build();
        simulatePipeline.init(input);
        simulatePipeline.startProcessing();
    }
}
