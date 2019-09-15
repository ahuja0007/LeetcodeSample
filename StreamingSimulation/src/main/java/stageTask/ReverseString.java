package stageTask;

import enums.Enrichment;
import requestPayload.Request;

import java.util.concurrent.BlockingQueue;

public class ReverseString implements IStage<String, String> {

    private BlockingQueue<Request<String, String>> inputQueue;
    private BlockingQueue<Request<String, String>> outQueue;
    private Enrichment enrichment;

    public void init(BlockingQueue<Request<String, String>> inputQueue, BlockingQueue<Request<String, String>> outputQueue) {
        enrichment = Enrichment.STRING_REVERSE;
        this.inputQueue = inputQueue;
        this.outQueue = outputQueue;
    }

    public void processRequest() {
        while (true) {
            Request<String, String> in = inputQueue.poll();
            if(in != null) {
                if(in.getProcessed() != null) {
                    in.setProcessed(new StringBuilder(in.getProcessed()).reverse().toString());
                    in.addEnrichment(enrichment);
                    outQueue.offer(in);
                } else {
                    // Pass the Exit Signal and Quit
                    outQueue.offer(in);
                    return;
                }
            }
        }
    }
}
