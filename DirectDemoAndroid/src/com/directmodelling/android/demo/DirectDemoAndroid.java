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

import static com.directmodelling.android.binding.Binding.bindCommand;
import static com.directmodelling.android.binding.Binding.bindDouble;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import com.directmodelling.demo.shared.DemoModel;
import com.directmodelling.impl.Command;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

@EActivity(R.layout.main)
public class DirectDemoAndroid extends Activity {
	// Enjoy this typesafety thank to the excellent RoboGuice
	// http://code.google.com/p/roboguice/
	@ViewById(R.id.NumberSlider)
	SeekBar bar;
	@ViewById(R.id.EditText01)
	EditText text;
	@ViewById
	Button startCalculator;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		final DemoModel demoModel = new DemoModel();
		bindDouble(bar, demoModel.doub());
		bindDouble(text, demoModel.doub());
		bindCommand(startCalculator, new Command() {
			@Override
			public void run() {
				startActivity(new Intent(DirectDemoAndroid.this,
						CalculatorActivity.class));
			}
		});
	}

}