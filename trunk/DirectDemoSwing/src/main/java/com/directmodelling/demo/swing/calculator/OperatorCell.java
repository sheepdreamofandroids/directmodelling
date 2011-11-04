package com.directmodelling.demo.swing.calculator;

import java.awt.Font;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

public class OperatorCell extends JPanel {
	private final JTextField textField = new JTextField();
	private final JLabel lblNewLabel = new JLabel("123");
	private final JLabel label = new JLabel("+");
	private final JLabel label_1 = new JLabel("=");

	/**
	 * Create the panel.
	 */
	public OperatorCell(String in) {
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		label.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		textField.setHorizontalAlignment(SwingConstants.RIGHT);
		textField.setText(in);
		textField.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		textField.setColumns(10);

		initComponents();
	}

	private void initComponents() {
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
			groupLayout.createSequentialGroup().addContainerGap().addComponent(label)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED).addComponent(label_1)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(66, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
			groupLayout
							.createSequentialGroup()
							.addContainerGap()
							.addGroup(
								groupLayout
												.createParallelGroup(Alignment.BASELINE)
												.addComponent(label)
												.addComponent(
													textField,
													GroupLayout.PREFERRED_SIZE,
													GroupLayout.DEFAULT_SIZE,
													GroupLayout.PREFERRED_SIZE).addComponent(label_1)
												.addComponent(lblNewLabel)).addContainerGap(40, Short.MAX_VALUE)));
		setLayout(groupLayout);
	}
}
