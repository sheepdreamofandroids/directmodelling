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
	public final JScrollPane scrollPane = new JScrollPane();
	public final JButton plus = new JButton("+");
	public final JButton minus = new JButton("-");
	public final JButton multiply = new JButton("*");
	public final JButton divide = new JButton("/");
	private final Box horizontalBox = Box.createHorizontalBox();
	private final Component horizontalGlue = Box.createHorizontalGlue();
	private final Component horizontalGlue_1 = Box.createHorizontalGlue();
	private final Component horizontalGlue_2 = Box.createHorizontalGlue();
	private final Box calculationList = Box.createVerticalBox();
	private final Component horizontalGlue_3 = Box.createHorizontalGlue();
	private final JButton clear = new JButton("CLR");

	/**
	 * Create the panel.
	 */
	public CalculatorPanel() {
		horizontalBox.add(plus);
		plus.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		{
			horizontalBox.add(horizontalGlue_2);
		}
		horizontalBox.add(minus);
		minus.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		{
			horizontalBox.add(horizontalGlue);
		}
		horizontalBox.add(multiply);
		multiply.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		{
			horizontalBox.add(horizontalGlue_1);
		}
		horizontalBox.add(divide);
		divide.setFont(new Font("Lucida Grande", Font.BOLD, 16));

		horizontalBox.add(horizontalGlue_3);

		horizontalBox.add(clear);

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
										.addComponent(scrollPane, Alignment.TRAILING,
												GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
										.addComponent(horizontalBox, Alignment.TRAILING,
												GroupLayout.DEFAULT_SIZE, 316, Short.MAX_VALUE))
						.addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
				Alignment.TRAILING,
				groupLayout
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(horizontalBox, GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));

		scrollPane.setViewportView(calculationList);
		setLayout(groupLayout);
	}

	public Box getCalculationList() {
		return calculationList;
	}

	public JButton getPlus() {
		return plus;
	}

	public JButton getMinus() {
		return minus;
	}

	public JButton getMultiply() {
		return multiply;
	}

	public JButton getDivide() {
		return divide;
	}

	public JButton getClear() {
		return clear;
	}
}
