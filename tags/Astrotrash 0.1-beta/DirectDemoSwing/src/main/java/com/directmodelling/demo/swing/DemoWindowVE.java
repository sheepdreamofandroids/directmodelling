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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Panel;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.directmodelling.demo.shared.DemoModel;
import com.directmodelling.swing.binding.Binding;


public class DemoWindowVE extends JFrame {

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setText(";lk;lk;lk");
		}
		return jCheckBox;
	}

	public void bind(final DemoModel m) {
		Binding.bind(getJSlider(), m.doub());
		Binding.bind(getJTextField(), m.doub());
	}

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null; // @jve:decl-index=0:visual-constraint="30,248"
	private Panel panel = null; // @jve:decl-index=0:
	private JLabel jLabel = null;
	private JTextField jTextField = null;
	private JLabel jLabel1 = null;
	private JSlider jSlider = null;
	private JLabel jLabel2 = null;
	private JCheckBox jCheckBox = null;

	/**
	 * This is the default constructor
	 */
	public DemoWindowVE() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(284, 86);
		setContentPane(getPanel());
		setTitle("JFrame");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.setSize(new Dimension(10, 10));
		}
		return jContentPane;
	}

	/**
	 * This method initializes panel
	 * 
	 * @return java.awt.Panel
	 */
	private Panel getPanel() {
		if (panel == null) {
			final GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			gridBagConstraints21.gridx = 1;
			gridBagConstraints21.gridy = 2;
			final GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 0;
			gridBagConstraints11.gridy = 2;
			jLabel2 = new JLabel();
			jLabel2.setText("JLabel");
			final GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = GridBagConstraints.BOTH;
			gridBagConstraints3.gridy = 1;
			gridBagConstraints3.weightx = 1.0;
			gridBagConstraints3.ipadx = 3;
			gridBagConstraints3.gridx = 1;
			final GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.ipadx = 3;
			gridBagConstraints2.gridy = 1;
			jLabel1 = new JLabel();
			jLabel1.setText("Slider");
			jLabel1.setHorizontalAlignment(SwingConstants.LEFT);
			final GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = GridBagConstraints.BOTH;
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.weightx = 1.0;
			gridBagConstraints1.ipadx = 3;
			gridBagConstraints1.gridx = 1;
			final GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.ipadx = 3;
			gridBagConstraints.gridy = 0;
			jLabel = new JLabel();
			jLabel.setText("Text");
			jLabel.setHorizontalAlignment(SwingConstants.LEFT);
			panel = new Panel();
			panel.setLayout(new GridBagLayout());
			panel.add(jLabel, gridBagConstraints);
			panel.add(getJTextField(), gridBagConstraints1);
			panel.add(jLabel1, gridBagConstraints2);
			panel.add(getJSlider(), gridBagConstraints3);
			panel.add(jLabel2, gridBagConstraints11);
			panel.add(getJCheckBox(), gridBagConstraints21);
		}
		initDataBindings();
		return panel;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
		}
		return jTextField;
	}

	/**
	 * This method initializes jSlider
	 * 
	 * @return javax.swing.JSlider
	 */
	private JSlider getJSlider() {
		if (jSlider == null) {
			jSlider = new JSlider();
		}
		return jSlider;
	}

	protected void initDataBindings() {
	}
} // @jve:decl-index=0:visual-constraint="107,10"
