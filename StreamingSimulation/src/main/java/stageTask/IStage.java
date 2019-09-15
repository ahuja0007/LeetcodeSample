package stageTask;

import requestPayload.Request;

import java.util.concurrent.BlockingQueue;

public interface IStage<I, O> {

    void init(BlockingQueue<Request<I, O>> inputQueue, BlockingQueue<Request<I, O>> outputQueue) throws Exception;

    void processRequest();
}
