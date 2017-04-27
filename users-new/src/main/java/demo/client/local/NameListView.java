package demo.client.local;

import org.kie.appformer.formmodeler.rendering.client.view.ListView;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import demo.client.shared.NameFormModel;
import demo.client.local.NameFormView;
import demo.client.shared.NameRestService;
import java.util.List;
import java.util.ArrayList;
import org.uberfire.ext.widgets.table.client.ColumnMeta;
import com.google.gwt.user.cellview.client.TextColumn;
import java.lang.Override;
import demo.client.shared.Name;

@Templated
public class NameListView extends ListView<Name, NameFormModel> {

	@Override
	public String getListTitle() {
		return "Name";
	}

	@Override
	public String getFormTitle() {
		return "Name Form";
	}

	@Override
	protected String getFormId() {
		return "Name Form";
	}

	@Override
	public List<ColumnMeta<Name>> getCrudColumns() {
		List<ColumnMeta<Name>> columnMetas = new ArrayList<>();
		ColumnMeta<Name> first_columnMeta = new ColumnMeta<Name>(
				new TextColumn<Name>() {
					@Override
					public String getValue(Name model) {
						Object value = model.getFirst();
						if (value == null) {
							return "";
						}
						return String.valueOf(value);
					}
				}, "First Name");
		columnMetas.add(first_columnMeta);
		ColumnMeta<Name> last_columnMeta = new ColumnMeta<Name>(
				new TextColumn<Name>() {
					@Override
					public String getValue(Name model) {
						Object value = model.getLast();
						if (value == null) {
							return "";
						}
						return String.valueOf(value);
					}
				}, "Last Name");
		columnMetas.add(last_columnMeta);
		return columnMetas;
	}

	@Override
	public NameFormModel createFormModel(Name name) {
		NameFormModel formModel = new NameFormModel();
		formModel.setName(name);
		return formModel;
	}

	public Name newModel() {
		return new Name();
	}
}