/**
 * *****************************************************************************
 * Copyright (c) 2010 Guus C. Bloemsma.
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>
 ******************************************************************************
 */
package com.directmodelling.demo.swing;

import com.directmodelling.api.Updates;
import com.directmodelling.impl.DirectModule;
import com.directmodelling.stm.Storage;
import com.directmodelling.stm.Version;
import com.directmodelling.stm.impl.TransactionImpl;
import com.directmodelling.stm.impl.VersionImpl;
import com.directmodelling.swing.SwingUpdateTracker;

import dagger.Component;

public class Main {

	@Component(modules = DirectModule.class)
	static interface SwingComponent {
		DirectModule.StorageProvider currentStorage();

		VersionImpl baseValues();
	}

	// public static void main(final String[] args) {
	// startup();
	// final DemoModel model = new DemoModel();
	// final DemoFrame f = new DemoFrame();
	// Binding.bindDouble(f.slider, model.doub());
	// Binding.bindDouble(f.textfield, model.doub());
	// SpinnerBinder.bindDouble(model.doub(), f.spinner, 1);
	// SwingCalculator.bind(f.calculatorPanel, SwingCalculator.MODEL);
	// // This is all presentation, no model necessary
	// f.startCalculator.setAction(new AbstractAction() {
	// @Override
	// public void actionPerformed(final ActionEvent e) {
	// SwingCalculator.start();
	// }
	// });
	// f.pack();
	// f.setVisible(true);
	// }

	public static void startup() {
		final Version baseData = new VersionImpl();
		Storage.current.init(new TransactionImpl(baseData));
		Updates.tracker = new SwingUpdateTracker();
	}
}
