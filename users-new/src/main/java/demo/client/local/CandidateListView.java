package demo.client.local;

import org.kie.appformer.formmodeler.rendering.client.view.ListView;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import demo.client.shared.CandidateFormModel;
import demo.client.local.CandidateFormView;
import demo.client.shared.CandidateRestService;
import java.util.List;
import java.util.ArrayList;
import org.uberfire.ext.widgets.table.client.ColumnMeta;
import com.google.gwt.user.cellview.client.TextColumn;
import java.lang.Override;
import demo.client.shared.Candidate;

@Templated
public class CandidateListView extends ListView<Candidate, CandidateFormModel> {

	@Override
	public String getListTitle() {
		return "Candidate";
	}

	@Override
	public String getFormTitle() {
		return "Candidate Form";
	}

	@Override
	protected String getFormId() {
		return "Candidate Form";
	}

	@Override
	public List<ColumnMeta<Candidate>> getCrudColumns() {
		List<ColumnMeta<Candidate>> columnMetas = new ArrayList<>();
		ColumnMeta<Candidate> notes_columnMeta = new ColumnMeta<Candidate>(
				new TextColumn<Candidate>() {
					@Override
					public String getValue(Candidate model) {
						Object value = model.getNotes();
						if (value == null) {
							return "";
						}
						return String.valueOf(value);
					}
				}, "HR Notes");
		columnMetas.add(notes_columnMeta);
		return columnMetas;
	}

	@Override
	public CandidateFormModel createFormModel(Candidate candidate) {
		CandidateFormModel formModel = new CandidateFormModel();
		formModel.setCandidate(candidate);
		return formModel;
	}

	public Candidate newModel() {
		return new Candidate();
	}
}