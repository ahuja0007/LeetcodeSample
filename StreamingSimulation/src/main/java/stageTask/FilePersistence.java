package stageTask;

import enums.Enrichment;
import lombok.Builder;
import requestPayload.Request;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;

@Builder
public class FilePersistence implements IStage<String, String> {

    private String outputFileName;
    private BlockingQueue<Request<String, String>> inputQueue;
    private Enrichment enrichment;
    private PrintWriter printWriter;

    @Override
    public void init(BlockingQueue<Request<String, String>> inputQueue, BlockingQueue<Request<String, String>> outputQueue) throws IOException {
        enrichment = Enrichment.FILE_PERSIST;
        printWriter = new PrintWriter(new BufferedWriter(new FileWriter(outputFileName)));
        this.inputQueue = inputQueue;
    }

    public void processRequest() {
        while (true) {
            Request<String, String> in = inputQueue.poll();
            if(in != null) {
                if(in.getToProcess() != null) {
                    in.addEnrichment(enrichment);
                    printWriter.println(in.getProcessed());
                }
                else {
                    // Close the FileWriter and return
                    printWriter.close();
                    return;
                }
            }
        }
    }
}
