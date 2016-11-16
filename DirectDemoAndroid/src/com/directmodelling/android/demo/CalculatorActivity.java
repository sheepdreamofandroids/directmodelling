package com.directmodelling.android.demo;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import com.directmodelling.android.binding.Binding;
import com.directmodelling.android.binding.Iterator2PanelBinding;
import com.directmodelling.api.DoubleValue;
import com.directmodelling.demo.shared.Calculator;
import com.directmodelling.demo.shared.FunctionApplication;
import com.google.common.base.Function;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

@EActivity(R.layout.calculator)
public class CalculatorActivity extends Activity {

	Calculator calculator = new Calculator();

	@ViewById(R.id.calculation)
	LinearLayout paperTape;
	@ViewById
	Button plus;
	@ViewById
	Button minus;
	@ViewById
	Button multiply;
	@ViewById
	Button divide;
	@ViewById
	Button clear;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.calculator);
		Binding.bindCommand(plus, calculator.plus());
		Binding.bindCommand(minus, calculator.minus());
		Binding.bindCommand(multiply, calculator.multiply());
		Binding.bindCommand(divide, calculator.divide());
		Binding.bindCommand(clear, calculator.clear());
		new Iterator2PanelBinding<DoubleValue>(paperTape,
				calculator.flattenedOperatorList,
				new Function<DoubleValue, View>() {

					@Override
					public View apply(final DoubleValue in) {
						final LayoutInflater layoutInflater = getLayoutInflater();
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
