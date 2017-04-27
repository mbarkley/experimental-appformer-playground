package demo.client.local;

import org.kie.appformer.formmodeler.rendering.client.view.FormView;
import java.util.List;
import java.util.ArrayList;
import demo.client.shared.NameFormModel;
import demo.client.shared.Name;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import javax.inject.Named;
import org.gwtbootstrap3.client.ui.TextBox;
import javax.inject.Inject;
import org.jboss.errai.ui.shared.api.annotations.Bound;
import org.jboss.errai.ui.shared.api.annotations.DataField;

@Templated
@Named("NameFormView")
public class NameFormView extends FormView<Name, NameFormModel> {

	@Inject
	@Bound(property = "name.first")
	@DataField
	private TextBox name_first;
	@Inject
	@Bound(property = "name.last")
	@DataField
	private TextBox name_last;

	@Override
	protected void initForm() {
		validator.registerInput("name_first", name_first);
		validator.registerInput("name_last", name_last);
	}

	@Override
	public void beforeDisplay() {
	}

	@Override
	public boolean doExtraValidations() {
		boolean valid = true;
		return valid;
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		name_first.setReadOnly(readOnly);
		name_last.setReadOnly(readOnly);
	}
}