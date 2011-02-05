/*******************************************************************************
 * Copyright (c) 2010 Guus C. Bloemsma.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 *******************************************************************************/
package com.directmodelling.android.demo;

import static com.directmodelling.android.binding.Binding.bindDouble;
import net.bloemsma.reactive.android.demo.R;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.SeekBar;

import com.directmodelling.demo.shared.DemoModel;
import com.directmodelling.impl.SimpleContext;
import com.directmodelling.stm.Storage;
import com.directmodelling.stm.impl.VersionImpl;

public class DirectDemoAndroid extends RoboActivity {
	// Enjoy this typesafety thank to the excellent RoboGuice
	// http://code.google.com/p/roboguice/
	@InjectView(R.id.NumberSlider)
	SeekBar bar;
	@InjectView(R.id.EditText01)
	EditText text;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initAndroid();
		final DemoModel demoModel = new DemoModel();
		bindDouble(bar, demoModel.doub());
		bindDouble(text, demoModel.doub());
	}

	private void initAndroid() {
		Storage.Util.current = new SimpleContext<Storage>(new VersionImpl());
	}
}