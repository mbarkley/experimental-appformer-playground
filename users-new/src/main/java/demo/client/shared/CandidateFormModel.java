package demo.client.shared;

import org.kie.appformer.formmodeler.rendering.client.shared.FormModel;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import javax.inject.Named;
import javax.validation.Valid;
import org.jboss.errai.common.client.api.annotations.MapsTo;

@Portable
@Bindable
@Named("CandidateFormModel")
public class CandidateFormModel extends FormModel<Candidate> {

	@Valid
	private Candidate candidate;

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	@Override
	public Candidate getModel() {
		return candidate;
	}

	@Override
	public void initModel() {
		candidate = new Candidate();
	}

	public CandidateFormModel() {
	}

	public CandidateFormModel(
			@MapsTo("candidate") demo.client.shared.Candidate candidate) {
		this.candidate = candidate;
	}
}