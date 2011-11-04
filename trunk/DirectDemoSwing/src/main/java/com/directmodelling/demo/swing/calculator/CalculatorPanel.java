package com.directmodelling.demo.swing.calculator;

import java.awt.Component;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;

public class CalculatorPanel extends JPanel {
	private final JScrollPane scrollPane = new JScrollPane();
	private final JButton button = new JButton("+");
	private final JButton button_1 = new JButton("-");
	private final JButton button_2 = new JButton("*");
	private final JButton button_3 = new JButton("/");
	private final Box horizontalBox = Box.createHorizontalBox();
	private final Component horizontalGlue = Box.createHorizontalGlue();
	private final Component horizontalGlue_1 = Box.createHorizontalGlue();
	private final Component horizontalGlue_2 = Box.createHorizontalGlue();

	/**
	 * Create the panel.
	 */
	public CalculatorPanel() {
		horizontalBox.add(button);
		button.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		{
			horizontalBox.add(horizontalGlue_2);
		}
		horizontalBox.add(button_1);
		button_1.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		{
			horizontalBox.add(horizontalGlue);
		}
		horizontalBox.add(button_2);
		button_2.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		{
			horizontalBox.add(horizontalGlue_1);
		}
		horizontalBox.add(button_3);
		button_3.setFont(new Font("Lucida Grande", Font.BOLD, 16));

		initComponents();
	}

	private void initComponents() {
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
			groupLayout
							.createSequentialGroup()
							.addContainerGap()
							.addGroup(
								groupLayout
												.createParallelGroup(Alignment.LEADING)
												.addComponent(
													scrollPane,
													Alignment.TRAILING,
													GroupLayout.DEFAULT_SIZE,
													323,
													Short.MAX_VALUE)
												.addComponent(
													horizontalBox,
													Alignment.TRAILING,
													GroupLayout.DEFAULT_SIZE,
													316,
													Short.MAX_VALUE)).addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
			Alignment.TRAILING,
			groupLayout
							.createSequentialGroup()
							.addContainerGap()
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(
								horizontalBox,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE).addContainerGap()));
		setLayout(groupLayout);
	}
}
