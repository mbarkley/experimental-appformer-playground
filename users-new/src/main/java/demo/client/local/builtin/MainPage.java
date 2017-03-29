package demo.client.local.builtin;

import static org.jboss.errai.common.client.dom.DOMUtil.removeAllElementChildren;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.jboss.errai.common.client.api.Caller;
import org.jboss.errai.common.client.api.IsElement;
import org.jboss.errai.common.client.dom.Div;
import org.jboss.errai.common.client.dom.Document;
import org.jboss.errai.common.client.dom.HTMLElement;
import org.jboss.errai.ui.nav.client.local.DefaultPage;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.PageShowing;
import org.jboss.errai.ui.nav.client.local.PageState;
import org.jboss.errai.ui.nav.client.local.api.LoginPage;
import org.jboss.errai.ui.nav.client.local.api.SecurityError;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.kie.appformer.flow.api.AppFlow;
import org.kie.appformer.flow.api.AppFlowExecutor;
import org.kie.appformer.flowset.interpreter.Interpreter;
import org.kie.workbench.common.stunner.core.graph.Graph;

import demo.client.shared.builtin.FlowService;

@Page( role = {DefaultPage.class, LoginPage.class, SecurityError.class} )
@Templated
public class MainPage {

    @PageState
    private String flow;

    @Inject @DataField
    private Div display;

    @Inject
    private Caller<FlowService> flowService;

    @Inject
    private Interpreter<?> interpreter;

    @Inject
    private AppFlowExecutor executor;

    @Inject
    private Document doc;

    @PageShowing
    private void showing() {
        if ( flow != null ) {
            flowService.call( ( final Graph graph ) -> {
                if ( graph != null ) {
                    final AppFlow generatedFlow = interpreter.convert( graph );
                    executor.execute( generatedFlow );
                }
                else {
                    final HTMLElement div = doc.createElement( "div" );
                    div.setTextContent( "No flow found." );
                    display( div );
                }
            } ).lookupFlowGraph( flow );
        }
    }

    public void display( @Observes final IsElement element ) {
        display( element.getElement() );
    }

    public void display( final HTMLElement element ) {
        removeAllElementChildren( display );
        display.appendChild( element );
    }

}
