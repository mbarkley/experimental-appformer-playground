package demo.client.shared;

import org.kie.appformer.formmodeler.rendering.client.shared.AppFormerRestService;
import javax.ws.rs.Path;
import demo.client.shared.Name;
import java.util.List;

@Path("name")
public interface NameRestService extends AppFormerRestService<Name> {
}