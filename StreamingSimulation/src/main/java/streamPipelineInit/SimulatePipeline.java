package streamPipelineInit;


import lombok.Builder;
import requestPayload.Request;
import stageTask.IStage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Builder
public class SimulatePipeline {

    private List<IStage> pipelineStages;
    private BlockingQueue<Request<String, String>> outQueue;
    private String inputFilename;
    private BufferedReader bf ;
    private ExecutorService executorService;

    public void init(String inputFile) throws Exception {
        List<BlockingQueue<Request<String, String>>> queues = new ArrayList<>();

        // Every Stage is a Thread as per Requirement
        executorService = Executors.newFixedThreadPool(pipelineStages.size());
        for (IStage stage : pipelineStages)
            queues.add(new LinkedBlockingQueue<>());

        for (int i = 0; i < pipelineStages.size(); i++) {
            if (outQueue == null) {
                outQueue = queues.get(i);
            }
            pipelineStages.get(i).init(queues.get(i), i != pipelineStages.size() - 1 ? queues.get(i + 1) : null);
        }
        bf = new BufferedReader(new FileReader(inputFile));
    }

    public void startProcessing(){
        for(IStage stage : pipelineStages){
            executorService.submit(stage::processRequest);
        }
        executorService.shutdown();

        try {
            String line;
            while ((line = bf.readLine()) != null) {
                outQueue.offer(new Request<>(line, line, new ArrayList<>()));
                Thread.sleep(1000);
            }
            // Using EOF to gracefully exit all the Threads in multiple stages.
            outQueue.offer(new Request<>(null, null, null));
            bf.close();

            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
