package demo.client.shared;

import org.kie.appformer.formmodeler.rendering.client.shared.AppFormerRestService;
import javax.ws.rs.Path;
import demo.client.shared.Candidate;
import java.util.List;

@Path("candidate")
public interface CandidateRestService extends AppFormerRestService<Candidate> {
}