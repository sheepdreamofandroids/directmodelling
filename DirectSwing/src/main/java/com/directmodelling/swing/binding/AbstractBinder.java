package com.directmodelling.swing.binding;

import java.util.Objects;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.directmodelling.api.Updates;
import com.directmodelling.api.Updates.Receiver;
import com.directmodelling.api.Updates.Tracker;
import com.directmodelling.api.Value;
import com.directmodelling.api.Value.Mutable;

public abstract class AbstractBinder<TVal> implements Receiver {
  // public interface Injector {
  // void inject(AbstractBinder<?> i);
  // }
  protected final Value<TVal> val;
  protected final Mutable<TVal> var;
  public Exception lastConversionError;
  private static final Logger log = LoggerFactory.getLogger(AbstractBinder.class);
  private Object currentWidgetValue = this; // special value meaning
  private Tracker tracker;
  // uninitialized
  @Inject
  void inject(final Tracker tracker) {
    this.tracker = tracker;
    tracker.registerForChanges(this);
  }

  @SuppressWarnings("unchecked")
  public AbstractBinder(final Value<TVal> val) {
    this.val = val;
    var = val instanceof Mutable ? (Mutable<TVal>) val : null;
    Updates.tracker.registerForChanges(this);
    //    Updates.tracker.aValueChanged(null);
  }

  /** Will be called whenever the variable changed with the converted value. */
  protected abstract void setWidgetValue(TVal v);

  /** Will be called to retrieve the value the user has set. */
  protected abstract TVal getWidgetValue();

  /**
   * Install a listener in your constructor and call this whenever the widget changed. It's up to
   * you how often this gets called. E.g. for a textfield this might be called on every keystroke or
   * only when it loses focus.
   */
  protected void widgetChanged() {
    if (var != null) {
      final TVal value = getWidgetValue();
      // avoid setting value redundantly
      if (currentWidgetValue == this || !Objects.equals(currentWidgetValue, value)) {
        var.setValue(value);
        currentWidgetValue = value;
      }
    }
  }

  @Override
  public void valuesChanged() {
    setWidgetValue(val.get());
  }
}
