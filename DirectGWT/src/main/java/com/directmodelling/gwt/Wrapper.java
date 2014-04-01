package com.directmodelling.gwt;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class Wrapper extends Composite {

	public Widget getWrappee() {
		final Widget wrap = getWidget();
		return wrap instanceof Wrapper ? ((Wrapper) wrap).getWrappee() : wrap;
	}

}
