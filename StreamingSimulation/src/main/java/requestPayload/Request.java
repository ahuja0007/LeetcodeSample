package requestPayload;

import enums.Enrichment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class Request<I, O> {
    // Initial Payload at start
    private I toProcess;

    // Payload after every stage
    private O processed;

    // May come handy for error handling in future
    private List<Enrichment> stagesProcessed;

    public void addEnrichment(Enrichment enrichment){
        if(stagesProcessed != null) {
            stagesProcessed.add(enrichment);
        }
    }
}
