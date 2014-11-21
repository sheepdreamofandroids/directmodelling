package com.directmodelling.collections

import com.directmodelling.api.Status
//TODO make separate
class DMList[T] extends ListRecorder[T] with Status.HasStatus {
	// Members declared in com.directmodelling.api.Status.HasStatus
	def status(): com.directmodelling.api.Status = Status.writeable
	// Members declared in java.util.List 
//	abstract override def containsAll(a: java.util.Collection[_]): Boolean = super.containsAll(a)
//	abstract override def removeAll(a: java.util.Collection[_]): Boolean = super.removeAll(a)
//	abstract override def retainAll(a: java.util.Collection[_]): Boolean = super.retainAll(a)
}
