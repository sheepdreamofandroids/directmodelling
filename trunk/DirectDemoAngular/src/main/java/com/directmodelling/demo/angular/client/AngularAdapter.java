package com.directmodelling.demo.angular.client;

import com.directmodelling.api.Status;
import com.directmodelling.api.Value;
import com.directmodelling.demo.angular.shared.PostcodeDemo;
import com.directmodelling.properties.HasMaximum;
import com.directmodelling.properties.HasMinimum;

public class AngularAdapter {

	// DemoModel model, Calculator calc,
	public static native void startAngular(final PostcodeDemo zip)/*-{
		$wnd
				.gwtStarted(
						function(s) {
							s.asInt = function(m) {
								return function(val) {
									if (val != undefined) {
										@com.directmodelling.demo.angular.client.AngularAdapter::setInt(Lcom/directmodelling/api/Value$Mutable;I)(m,val);
									}
									return @com.directmodelling.demo.angular.client.AngularAdapter::getInt(Lcom/directmodelling/api/Value;)(m);
								};
							};
							s.asText = function(m) {
								return function(val) {
									if (val != undefined) {
										@com.directmodelling.demo.angular.client.AngularAdapter::set(Lcom/directmodelling/api/Value$Mutable;Ljava/lang/Object;)(m, val);
									}
									return @com.directmodelling.demo.angular.client.AngularAdapter::get(Lcom/directmodelling/api/Value;)(m);
								};
							};
							s.get = function(o) {return o.@com.directmodelling.api.Value::getValue()();};
							s.set = function(o,v) {return o.@com.directmodelling.api.Value.Mutable::setValue(TT;)(v);};
						 	s.status = @com.directmodelling.demo.angular.client.AngularAdapter::status(Ljava/lang/Object;);
							s.minimum = @com.directmodelling.demo.angular.client.AngularAdapter::getIntMin(Ljava/lang/Object;);
							s.maximum = @com.directmodelling.demo.angular.client.AngularAdapter::maximum(Ljava/lang/Object;);
							s.log = function(o) {console.log(o);};
						}, zip);
	}-*/;

	public static void setInt(Value.Mutable<Integer> m, int i) {
		m.setValue(i);
	}

	public static int getInt(Value<Integer> m) {
		return m.getValue();
	}

	public static double getIntMin(Object o) {
		if (!(o instanceof HasMinimum))
			return 0;
		Comparable min = ((HasMinimum) o).getMinimum();
		if (!(min instanceof Number))
			return 0;
		return ((Number) min).doubleValue();
	}

	public static double maximum(Object o) {
		if (!(o instanceof HasMaximum))
			return 0;
		Comparable max = ((HasMaximum) o).getMaximum();
		if (!(max instanceof Number))
			return 0;
		return ((Number) max).doubleValue();
	}

	public static void set(Value.Mutable<Object> m, Object o) {
		m.setValue(o);
	}

	public static Object get(Value<Object> v) {
		return v.getValue();
	}

	public static Status status(Object o) {
		return Status.readonly.unlessFrom(o);
	}
}
