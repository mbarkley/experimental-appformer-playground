package demo.server;

import demo.client.shared.Name;
import java.util.List;
import demo.client.shared.NameRestService;
import javax.inject.Inject;
import javax.ejb.Stateless;
import org.kie.appformer.formmodeler.rendering.client.shared.query.QueryCriteria;

@Stateless
public class NameRestServiceImpl implements NameRestService {

	@Inject
	private NameEntityService entityService;

	@Override
	public Name create(Name model) {
		return entityService.create(model);
	}

	public Name lookup(long id) {
		return entityService.lookup(Name.class, id);
	}

	@Override
	public List<Name> load() {
		return entityService.listAll(Name.class);
	}

	@Override
	public List<Name> load(int start, int end) {
		return entityService.list(Name.class, start, end);
	}

	@Override
	public Boolean update(Name model) {
		entityService.update(model);
		return true;
	}

	@Override
	public Boolean delete(Name model) {
		entityService.delete(model);
		return true;
	}

	@Override
	public List<Name> list(QueryCriteria criteria) {
		return entityService.list(Name.class, criteria);
	}
}