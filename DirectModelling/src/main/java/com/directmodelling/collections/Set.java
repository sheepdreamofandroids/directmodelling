package com.directmodelling.collections;

import com.directmodelling.collections.Delta.HasDeltas;
import com.directmodelling.collections.operators.SetDelta;

public interface Set<Element> extends HasDeltas<SetDelta<Element>>, java.util.Set<Element> {

}
