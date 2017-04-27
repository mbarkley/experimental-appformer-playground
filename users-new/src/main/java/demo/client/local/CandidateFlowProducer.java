package demo.client.local;

import demo.client.shared.Candidate;
import demo.client.shared.CandidateFormModel;
import demo.client.shared.CandidateRestService;
import org.kie.appformer.flow.api.Unit;
import java.util.Optional;
import org.kie.appformer.flow.api.Command;
import org.kie.appformer.flow.api.FormOperation;
import org.kie.appformer.formmodeler.rendering.client.flow.FlowProducer;
import org.jboss.errai.ioc.client.api.EntryPoint;
import javax.inject.Singleton;
import javax.enterprise.inject.Produces;
import org.kie.appformer.flow.api.AppFlow;
import org.kie.appformer.formmodeler.rendering.client.flow.ForEntity;
import javax.inject.Named;

@EntryPoint
public class CandidateFlowProducer
		extends
			FlowProducer<Candidate, CandidateFormModel, CandidateFormView, CandidateListView, CandidateRestService> {

	@Override
	public CandidateFormModel modelToFormModel(Candidate model) {
		return new CandidateFormModel(model);
	}

	@Override
	public Candidate formModelToModel(CandidateFormModel formModel) {
		return formModel.getCandidate();
	}

	@Override
	public Candidate newModel() {
		return new Candidate();
	}

	@Override
	public Class<Candidate> getModelType() {
		return Candidate.class;
	}

	@Override
	public Class<CandidateFormModel> getFormModelType() {
		return CandidateFormModel.class;
	}

	@Singleton
	@Produces
	public Class<Candidate> entityType() {
		return Candidate.class;
	}

	@Override
	@Produces
	@Singleton
	@ForEntity("demo.client.shared.Candidate")
	@Named("create")
	public AppFlow<Unit, Command<FormOperation, CandidateFormModel>> create() {
		return super.create();
	}

	@Override
	@Produces
	@Singleton
	@ForEntity("demo.client.shared.Candidate")
	@Named("crud")
	public AppFlow<Unit, Unit> crud() {
		return super.crud();
	}

	@Override
	@Produces
	@Singleton
	@ForEntity("demo.client.shared.Candidate")
	@Named("createAndReview")
	public AppFlow<Unit, Unit> createAndReview() {
		return super.createAndReview();
	}

	@Override
	@Produces
	@Singleton
	@ForEntity("demo.client.shared.Candidate")
	@Named("view")
	public AppFlow<Unit, Unit> view() {
		return super.view();
	}
}