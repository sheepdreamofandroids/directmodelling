package com.directmodelling.demo.gwt.client.calculator;

import com.directmodelling.api.DoubleValue;
import com.directmodelling.demo.shared.Calculator;
import com.directmodelling.demo.shared.FunctionApplication;
import com.directmodelling.gwt.BoundTextbox;
import com.directmodelling.gwt.Iterator2PanelBinding;
import com.directmodelling.impl.util.Function;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class CalculatorPanel extends Composite {

	private static CalculatorPanelUiBinder uiBinder = GWT.create(CalculatorPanelUiBinder.class);
	@UiField(provided = true)
	final Calculator calculator;
	@UiField
	Panel tape;

	interface CalculatorPanelUiBinder extends UiBinder<Widget, CalculatorPanel> {
	}

	public CalculatorPanel(Calculator calculator) {
		this.calculator = calculator;
		initWidget(uiBinder.createAndBindUi(this));
		new Iterator2PanelBinding<DoubleValue>(tape, calculator.flattenedOperatorList,
						new Function<DoubleValue, IsWidget>() {

							@Override
							public IsWidget apply(DoubleValue in) {
								if (in instanceof FunctionApplication)
									return new FunctionApplicationPanel((FunctionApplication) in);
								final BoundTextbox boundTextbox = new BoundTextbox();
								boundTextbox.setDoubleVar(in);
								return boundTextbox;
							}
						});
	}

}
