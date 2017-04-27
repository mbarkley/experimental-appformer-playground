package demo.client.local;

import org.kie.appformer.formmodeler.rendering.client.view.FormView;
import java.util.List;
import java.util.ArrayList;
import demo.client.shared.AddressFormModel;
import demo.client.shared.Address;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import javax.inject.Named;
import org.gwtbootstrap3.client.ui.TextBox;
import javax.inject.Inject;
import org.jboss.errai.ui.shared.api.annotations.Bound;
import org.jboss.errai.ui.shared.api.annotations.DataField;

@Templated
@Named("AddressFormView")
public class AddressFormView extends FormView<Address, AddressFormModel> {

	@Inject
	@Bound(property = "address.street")
	@DataField
	private TextBox address_street;
	@Inject
	@Bound(property = "address.num")
	@DataField
	private TextBox address_num;

	@Override
	protected void initForm() {
		validator.registerInput("address_street", address_street);
		validator.registerInput("address_num", address_num);
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
		address_street.setReadOnly(readOnly);
		address_num.setReadOnly(readOnly);
	}
}