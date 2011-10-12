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
package com.directmodelling.demo.swing;

import javax.swing.JFrame;

import com.directmodelling.api.Updates;
import com.directmodelling.demo.shared.DemoModel;
import com.directmodelling.stm.Storage;
import com.directmodelling.stm.Storage.Util;
import com.directmodelling.stm.impl.TransactionImpl;
import com.directmodelling.stm.impl.VersionImpl;
import com.directmodelling.swing.SwingContext;
import com.directmodelling.swing.SwingUpdateTracker;

public class Main {

	public static void main(String[] args) {
		final VersionImpl baseData = new VersionImpl();
		Util.current = new SwingContext<Storage>(new TransactionImpl(baseData));
		Updates.tracker = new SwingUpdateTracker();
		JFrame frame = new JFrame("Demo");
		frame.setContentPane(new DemoPanel(new DemoModel()));
		frame.pack();
		frame.setVisible(true);
	}
}
