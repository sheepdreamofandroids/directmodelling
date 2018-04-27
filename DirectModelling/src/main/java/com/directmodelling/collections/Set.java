package com.directmodelling.collections;

import com.directmodelling.collections.operators.SetDelta;

public interface Set<Element>
		extends HasDeltas<SetDelta<Element>, java.util.Set<Element>>, java.util.Set<Element> {

}
