package demo.client.local;

import org.kie.appformer.formmodeler.rendering.client.view.FormView;
import java.util.List;
import java.util.ArrayList;
import demo.client.shared.CandidateFormModel;
import demo.client.shared.Candidate;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import javax.inject.Named;
import org.kie.appformer.formmodeler.rendering.client.shared.fields.SubForm;
import demo.client.shared.Name;
import demo.client.shared.NameFormModel;
import demo.client.local.NameFormView;
import org.kie.appformer.formmodeler.rendering.client.shared.fields.SubFormModelAdapter;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import demo.client.shared.Address;
import demo.client.shared.AddressFormModel;
import demo.client.local.AddressFormView;
import org.gwtbootstrap3.client.ui.TextBox;
import javax.inject.Inject;
import org.jboss.errai.ui.shared.api.annotations.Bound;

@Templated
@Named("CandidateFormView")
public class CandidateFormView extends FormView<Candidate, CandidateFormModel> {

	@DataField
	private SubForm candidate_name = new SubForm(
			new Candidate_nameSubFormModelAdapter());
	@DataField
	private SubForm candidate_address = new SubForm(
			new Candidate_addressSubFormModelAdapter());
	@Inject
	@Bound(property = "candidate.notes")
	@DataField
	private TextBox candidate_notes;

	@Override
	protected void initForm() {
		validator.registerInput("candidate_name", candidate_name);
		updateNestedModels(true);
		validator.registerInput("candidate_address", candidate_address);
		validator.registerInput("candidate_notes", candidate_notes);
	}

	@Override
	public void beforeDisplay() {
	}

	@Override
	public boolean doExtraValidations() {
		boolean valid = true;
		if (!candidate_address.validate() && valid) {
			valid = false;
		}
		if (!candidate_name.validate() && valid) {
			valid = false;
		}
		return valid;
	}

	public class Candidate_nameSubFormModelAdapter
			implements
				SubFormModelAdapter<Name, NameFormModel> {
		@Override
		public Class<NameFormView> getFormViewType() {
			return NameFormView.class;
		}

		@Override
		public NameFormModel getFormModelForModel(Name model) {
			return new NameFormModel(model);
		}
	}

	@Override
	protected void updateNestedModels(boolean init) {
		demo.client.shared.Name name = getModel().getCandidate().getName();
		if (name == null && init) {
			name = new demo.client.shared.Name();
			getModel().getCandidate().setName(name);
		}
		candidate_name.setModel(name);
		demo.client.shared.Address address = getModel().getCandidate()
				.getAddress();
		if (address == null && init) {
			address = new demo.client.shared.Address();
			getModel().getCandidate().setAddress(address);
		}
		candidate_address.setModel(address);
	}

	@Override
	public void setModel(CandidateFormModel model) {
		super.setModel(model);
		updateNestedModels(false);
	}

	public class Candidate_addressSubFormModelAdapter
			implements
				SubFormModelAdapter<Address, AddressFormModel> {
		@Override
		public Class<AddressFormView> getFormViewType() {
			return AddressFormView.class;
		}

		@Override
		public AddressFormModel getFormModelForModel(Address model) {
			return new AddressFormModel(model);
		}
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		candidate_name.setReadOnly(readOnly);
		candidate_address.setReadOnly(readOnly);
		candidate_notes.setReadOnly(readOnly);
	}
}