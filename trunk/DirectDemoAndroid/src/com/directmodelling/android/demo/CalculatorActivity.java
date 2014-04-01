package com.directmodelling.android.demo;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.directmodelling.android.binding.Binding;
import com.directmodelling.android.binding.Iterator2PanelBinding;
import com.directmodelling.api.DoubleValue;
import com.directmodelling.demo.shared.Calculator;
import com.directmodelling.demo.shared.FunctionApplication;
import com.google.common.base.Function;

public class CalculatorActivity extends RoboActivity {

	Calculator calculator = new Calculator();

	@InjectView(R.id.calculation)
	LinearLayout paperTape;
	@InjectView(R.id.plus)
	Button plus;
	@InjectView(R.id.minus)
	Button minus;
	@InjectView(R.id.multiply)
	Button multiply;
	@InjectView(R.id.divide)
	Button divide;
	@InjectView(R.id.clear)
	Button clear;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calculator);
		Binding.bindCommand(plus, calculator.plus());
		Binding.bindCommand(minus, calculator.minus());
		Binding.bindCommand(multiply, calculator.multiply());
		Binding.bindCommand(divide, calculator.divide());
		Binding.bindCommand(clear, calculator.clear());
		final LayoutInflater layoutInflater = getLayoutInflater();
		new Iterator2PanelBinding<DoubleValue>(paperTape,
				calculator.flattenedOperatorList,
				new Function<DoubleValue, View>() {

					@Override
					public View apply(final DoubleValue in) {
						View result;
						if (in instanceof FunctionApplication) {
							final FunctionApplication fa = (FunctionApplication) in;
							result = layoutInflater.inflate(
									R.layout.function_application, paperTape,
									false);
							Binding.bindDouble((TextView) result
									.findViewById(R.id.argument2), fa.right());
							Binding.bindVal((TextView) result
									.findViewById(R.id.operator), fa.operator());
							Binding.bindDouble(
									(TextView) result.findViewById(R.id.result),
									in);
						} else {
							result = layoutInflater.inflate(
									R.layout.first_argument, paperTape, false);
							Binding.bindDouble((TextView) result
									.findViewById(R.id.argument), in);
						}
						return result;
					}
				});
	}
}
