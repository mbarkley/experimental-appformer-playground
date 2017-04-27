package demo.client.shared;

import org.kie.appformer.formmodeler.rendering.client.shared.FormModel;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import javax.inject.Named;
import javax.validation.Valid;
import org.jboss.errai.common.client.api.annotations.MapsTo;

@Portable
@Bindable
@Named("NameFormModel")
public class NameFormModel extends FormModel<Name> {

	@Valid
	private Name name;

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	@Override
	public Name getModel() {
		return name;
	}

	@Override
	public void initModel() {
		name = new Name();
	}

	public NameFormModel() {
	}

	public NameFormModel(@MapsTo("name") demo.client.shared.Name name) {
		this.name = name;
	}
}