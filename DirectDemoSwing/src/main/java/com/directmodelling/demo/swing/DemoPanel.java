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

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

import com.directmodelling.api.Updates;
import com.directmodelling.demo.shared.IDemoModel;
import com.directmodelling.swing.binding.Binding;

public class DemoPanel extends JPanel {

	private final IDemoModel model;
	private final JTextField textBox;

	/**
	 * Create the panel.
	 */
	public DemoPanel(IDemoModel model) {
		this.model = model;
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 100, 305, 0 };
		gridBagLayout.rowHeights = new int[] { 21, 27, 37, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel label = new JLabel("New label");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.EAST;
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		add(label, gbc_label);

		JSlider slider = new JSlider();
		GridBagConstraints gbc_slider = new GridBagConstraints();
		gbc_slider.anchor = GridBagConstraints.NORTH;
		gbc_slider.fill = GridBagConstraints.HORIZONTAL;
		gbc_slider.insets = new Insets(0, 0, 5, 0);
		gbc_slider.gridx = 1;
		gbc_slider.gridy = 0;
		add(slider, gbc_slider);

		JLabel lblSecondField = new JLabel("second field");
		GridBagConstraints gbc_lblSecondField = new GridBagConstraints();
		gbc_lblSecondField.anchor = GridBagConstraints.EAST;
		gbc_lblSecondField.insets = new Insets(0, 0, 5, 5);
		gbc_lblSecondField.gridx = 0;
		gbc_lblSecondField.gridy = 1;
		add(lblSecondField, gbc_lblSecondField);

		textBox = new JTextField();
		GridBagConstraints gbc_textBox = new GridBagConstraints();
		gbc_textBox.anchor = GridBagConstraints.NORTH;
		gbc_textBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_textBox.insets = new Insets(0, 0, 5, 0);
		gbc_textBox.gridx = 1;
		gbc_textBox.gridy = 1;
		add(textBox, gbc_textBox);

		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel.anchor = GridBagConstraints.NORTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 2;
		add(panel, gbc_panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton btnCancel = new JButton("Cancel");
		panel.add(btnCancel);

		JButton btnOk = new JButton("OK");
		panel.add(btnOk);

		final JPanel container = new JPanel();
		GridBagConstraints gbc_container = new GridBagConstraints();
		gbc_container.gridheight = 3;
		gbc_container.gridwidth = 2;
		gbc_container.insets = new Insets(0, 0, 0, 5);
		gbc_container.fill = GridBagConstraints.BOTH;
		gbc_container.gridx = 0;
		gbc_container.gridy = 3;
		add(container, gbc_container);

		// These bindings are handwritten
		Binding.bindDouble(slider, model.doub());
		// Binding.bind(textBox, model.doub());
		final List<String> x = new ArrayList<String>();
		new Timer().schedule(new TimerTask() {

			@Override
			public void run() {
				x.add("" + System.currentTimeMillis());
				Updates.aValueChanged(null);
			}
		}, 0, 1000);

		// new Iterator2PanelBinding<String>(container, x, new Function<String,
		// Component>() {
		// @Override
		// public Component apply(String in) {
		// return new OperatorCell(in);
		// }
		// });
	}

}
