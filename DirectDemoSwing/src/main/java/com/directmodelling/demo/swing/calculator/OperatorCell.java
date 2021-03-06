package com.directmodelling.demo.swing.calculator;

import java.awt.Font;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import com.directmodelling.api.Value;
import com.directmodelling.demo.shared.FunctionApplication;
import com.directmodelling.impl.conversion.TextFromDouble;
import com.directmodelling.swing.binding.DocumentBinder;
import com.directmodelling.swing.binding.ListModelBinding;
import com.directmodelling.swing.binding.ReadOnlyBinding;

public class OperatorCell extends JPanel {
	private final JTextField textField = new JTextField();
	private final JLabel lblNewLabel = new JLabel("123");
	private final JLabel label_1 = new JLabel("=");
	private final JComboBox operatorChooser = new JComboBox();

	/**
	 * Create the panel.
	 */
	public OperatorCell() {
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		textField.setHorizontalAlignment(SwingConstants.RIGHT);
		textField.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		textField.setColumns(10);

		initComponents();
	}

	public OperatorCell(final FunctionApplication in) {
		this();
		new DocumentBinder(textField, new TextFromDouble(in.right()));
		// label.setText(in.operator().getValue().name);
		ReadOnlyBinding.bind(lblNewLabel, in);
		ListModelBinding.bind(operatorChooser, in.operator());
	}

	public OperatorCell(final Value<Double> in) {
		this();
		new DocumentBinder(textField, new TextFromDouble(in));
		// label.setText(in.operator().getValue().name);
		ReadOnlyBinding.bind(lblNewLabel, in);
		operatorChooser.setVisible(false);
	}

	private void initComponents() {
		final GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(operatorChooser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(label_1)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(99, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addGroup(
								groupLayout
										.createParallelGroup(Alignment.BASELINE)
										.addComponent(operatorChooser, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE).addComponent(label_1)
										.addComponent(lblNewLabel)).addContainerGap(8, Short.MAX_VALUE)));
		setLayout(groupLayout);
	}
}
