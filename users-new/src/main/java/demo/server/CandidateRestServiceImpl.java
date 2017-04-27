package demo.server;

import demo.client.shared.Candidate;
import java.util.List;
import demo.client.shared.CandidateRestService;
import javax.inject.Inject;
import javax.ejb.Stateless;
import org.kie.appformer.formmodeler.rendering.client.shared.query.QueryCriteria;

@Stateless
public class CandidateRestServiceImpl implements CandidateRestService {

	@Inject
	private CandidateEntityService entityService;

	@Override
	public Candidate create(Candidate model) {
		return entityService.create(model);
	}

	public Candidate lookup(long id) {
		return entityService.lookup(Candidate.class, id);
	}

	@Override
	public List<Candidate> load() {
		return entityService.listAll(Candidate.class);
	}

	@Override
	public List<Candidate> load(int start, int end) {
		return entityService.list(Candidate.class, start, end);
	}

	@Override
	public Boolean update(Candidate model) {
		entityService.update(model);
		return true;
	}

	@Override
	public Boolean delete(Candidate model) {
		entityService.delete(model);
		return true;
	}

	@Override
	public List<Candidate> list(QueryCriteria criteria) {
		return entityService.list(Candidate.class, criteria);
	}
}