package demo.client.local;

import demo.client.shared.Name;
import demo.client.shared.NameFormModel;
import demo.client.shared.NameRestService;
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
public class NameFlowProducer
		extends
			FlowProducer<Name, NameFormModel, NameFormView, NameListView, NameRestService> {

	@Override
	public NameFormModel modelToFormModel(Name model) {
		return new NameFormModel(model);
	}

	@Override
	public Name formModelToModel(NameFormModel formModel) {
		return formModel.getName();
	}

	@Override
	public Name newModel() {
		return new Name();
	}

	@Override
	public Class<Name> getModelType() {
		return Name.class;
	}

	@Override
	public Class<NameFormModel> getFormModelType() {
		return NameFormModel.class;
	}

	@Singleton
	@Produces
	public Class<Name> entityType() {
		return Name.class;
	}

	@Override
	@Produces
	@Singleton
	@ForEntity("demo.client.shared.Name")
	@Named("create")
	public AppFlow<Unit, Command<FormOperation, NameFormModel>> create() {
		return super.create();
	}

	@Override
	@Produces
	@Singleton
	@ForEntity("demo.client.shared.Name")
	@Named("crud")
	public AppFlow<Unit, Unit> crud() {
		return super.crud();
	}

	@Override
	@Produces
	@Singleton
	@ForEntity("demo.client.shared.Name")
	@Named("createAndReview")
	public AppFlow<Unit, Unit> createAndReview() {
		return super.createAndReview();
	}

	@Override
	@Produces
	@Singleton
	@ForEntity("demo.client.shared.Name")
	@Named("view")
	public AppFlow<Unit, Unit> view() {
		return super.view();
	}
}