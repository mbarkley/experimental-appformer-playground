package demo.client.local;

import demo.client.shared.Address;
import demo.client.shared.AddressFormModel;
import demo.client.shared.AddressRestService;
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
public class AddressFlowProducer
		extends
			FlowProducer<Address, AddressFormModel, AddressFormView, AddressListView, AddressRestService> {

	@Override
	public AddressFormModel modelToFormModel(Address model) {
		return new AddressFormModel(model);
	}

	@Override
	public Address formModelToModel(AddressFormModel formModel) {
		return formModel.getAddress();
	}

	@Override
	public Address newModel() {
		return new Address();
	}

	@Override
	public Class<Address> getModelType() {
		return Address.class;
	}

	@Override
	public Class<AddressFormModel> getFormModelType() {
		return AddressFormModel.class;
	}

	@Singleton
	@Produces
	public Class<Address> entityType() {
		return Address.class;
	}

	@Override
	@Produces
	@Singleton
	@ForEntity("demo.client.shared.Address")
	@Named("create")
	public AppFlow<Unit, Command<FormOperation, AddressFormModel>> create() {
		return super.create();
	}

	@Override
	@Produces
	@Singleton
	@ForEntity("demo.client.shared.Address")
	@Named("crud")
	public AppFlow<Unit, Unit> crud() {
		return super.crud();
	}

	@Override
	@Produces
	@Singleton
	@ForEntity("demo.client.shared.Address")
	@Named("createAndReview")
	public AppFlow<Unit, Unit> createAndReview() {
		return super.createAndReview();
	}

	@Override
	@Produces
	@Singleton
	@ForEntity("demo.client.shared.Address")
	@Named("view")
	public AppFlow<Unit, Unit> view() {
		return super.view();
	}
}