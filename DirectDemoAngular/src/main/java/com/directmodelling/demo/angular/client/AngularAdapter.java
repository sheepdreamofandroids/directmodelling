package com.directmodelling.demo.angular.client;

import com.directmodelling.api.Status.HasStatus;
import com.directmodelling.api.Value;
import com.directmodelling.demo.angular.shared.PostcodeDemo;

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
							s.statusX = function(s) {
								return @com.directmodelling.demo.angular.client.AngularAdapter::status(Lcom/directmodelling/api/Status$HasStatus;)(s);
							};
						}, zip);
	}-*/;

	public static void setInt(Value.Mutable<Integer> m, int i) {
		m.setValue(i);
	}

	public static int getInt(Value<Integer> m) {
		return m.getValue();
	}

	public static void set(Value.Mutable<Object> m, Object o) {
		m.setValue(o);
	}

	public static Object get(Value<Object> v) {
		return v.getValue();
	}

	public static String status(HasStatus s) {
		return s.toString();
	}

}
