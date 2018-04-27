package com.directmodelling.demo.gwt.client.calculator;

import com.directmodelling.demo.shared.FunctionApplication;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class FunctionApplicationPanel extends Composite {

	private static FunctionApplicationPanelUiBinder uiBinder = GWT.create(FunctionApplicationPanelUiBinder.class);
	@UiField(provided = true)
	FunctionApplication functionApplication;

	interface FunctionApplicationPanelUiBinder extends UiBinder<Widget, FunctionApplicationPanel> {
	}

	public FunctionApplicationPanel(final FunctionApplication functionApplication) {
		this.functionApplication = functionApplication;
		initWidget(uiBinder.createAndBindUi(this));
	}

}
