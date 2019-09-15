package stageTask;

import enums.Enrichment;
import requestPayload.Request;

import java.util.Base64;
import java.util.concurrent.BlockingQueue;

public class Base64Encode implements IStage<String, String> {

    private BlockingQueue<Request<String, String>> inputQueue;
    private BlockingQueue<Request<String, String>> outQueue;
    private Enrichment enrichment;

    @Override
    public void init(BlockingQueue<Request<String, String>> inputQueue, BlockingQueue<Request<String, String>> outputQueue) {
        enrichment = Enrichment.BASE64_ENCODE;
        this.inputQueue = inputQueue;
        this.outQueue = outputQueue;
    }

    public void processRequest() {
        while (true) {
            Request<String, String> in = inputQueue.poll();
            if(in != null) {
                if(in.getToProcess() != null) {
                    in.setProcessed(Base64.getEncoder().encodeToString(in.getProcessed().getBytes()));
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
