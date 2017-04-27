package demo.client.shared;

import org.kie.appformer.formmodeler.rendering.client.shared.AppFormerRestService;
import javax.ws.rs.Path;
import demo.client.shared.Address;
import java.util.List;

@Path("address")
public interface AddressRestService extends AppFormerRestService<Address> {
}