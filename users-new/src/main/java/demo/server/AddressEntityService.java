package demo.server;

import org.kie.appformer.formmodeler.rendering.server.rest.BaseEntityService;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;

@Stateless
@TransactionAttribute(javax.ejb.TransactionAttributeType.REQUIRES_NEW)
public class AddressEntityService extends BaseEntityService {
}