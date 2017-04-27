package demo.client.shared;

import org.kie.appformer.formmodeler.rendering.client.shared.FormModel;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import javax.inject.Named;
import javax.validation.Valid;
import org.jboss.errai.common.client.api.annotations.MapsTo;

@Portable
@Bindable
@Named("AddressFormModel")
public class AddressFormModel extends FormModel<Address> {

	@Valid
	private Address address;

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public Address getModel() {
		return address;
	}

	@Override
	public void initModel() {
		address = new Address();
	}

	public AddressFormModel() {
	}

	public AddressFormModel(
			@MapsTo("address") demo.client.shared.Address address) {
		this.address = address;
	}
}