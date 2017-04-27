package demo.server;

import demo.client.shared.Address;
import java.util.List;
import demo.client.shared.AddressRestService;
import javax.inject.Inject;
import javax.ejb.Stateless;
import org.kie.appformer.formmodeler.rendering.client.shared.query.QueryCriteria;

@Stateless
public class AddressRestServiceImpl implements AddressRestService {

	@Inject
	private AddressEntityService entityService;

	@Override
	public Address create(Address model) {
		return entityService.create(model);
	}

	public Address lookup(long id) {
		return entityService.lookup(Address.class, id);
	}

	@Override
	public List<Address> load() {
		return entityService.listAll(Address.class);
	}

	@Override
	public List<Address> load(int start, int end) {
		return entityService.list(Address.class, start, end);
	}

	@Override
	public Boolean update(Address model) {
		entityService.update(model);
		return true;
	}

	@Override
	public Boolean delete(Address model) {
		entityService.delete(model);
		return true;
	}

	@Override
	public List<Address> list(QueryCriteria criteria) {
		return entityService.list(Address.class, criteria);
	}
}