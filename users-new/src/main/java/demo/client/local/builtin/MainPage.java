package demo.client.local.builtin;

import static org.jboss.errai.common.client.dom.DOMUtil.removeAllElementChildren;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.jboss.errai.common.client.api.IsElement;
import org.jboss.errai.common.client.dom.Div;
import org.jboss.errai.ui.nav.client.local.DefaultPage;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.PageShowing;
import org.jboss.errai.ui.nav.client.local.api.LoginPage;
import org.jboss.errai.ui.nav.client.local.api.SecurityError;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.kie.appformer.flow.api.AppFlowExecutor;

@Page( role = {DefaultPage.class, LoginPage.class, SecurityError.class} )
@Templated
public class MainPage {

    @Inject
    private FlowLoader loader;

    @Inject
    private AppFlowExecutor executor;

    @Inject @DataField
    private Div display;

    @PageShowing
    private void showing() {
        loader.getMainFlow( flow -> executor.execute( flow ) );
    }

    public void display( @Observes final IsElement element ) {
        removeAllElementChildren( display );
        display.appendChild( element.getElement() );
    }

}
