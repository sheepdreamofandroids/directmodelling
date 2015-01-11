package com.directmodelling.android.binding;

import android.view.View;
import android.view.View.OnClickListener;

import com.directmodelling.impl.Command;

public class ClickBinder extends Binder<View, Void, Void> implements
		OnClickListener {

	private final Command command;

	public ClickBinder(final Command m, final View upgradeButton) {
		super(m, upgradeButton);
		this.command = m;
		upgradeButton.setOnClickListener(this);
	}

	@Override
	protected void setViewEnabled(final boolean b) {
		view.setEnabled(b);
	}

	@Override
	protected void setViewValue(final Void v, final boolean force) {
		// no value
	}

	@Override
	protected Void getViewValue() {
		// no value
		return null;
	}

	@Override
	public void onClick(final View v) {
		if (command.status().isEnabled())
			command.run();
	}

}
