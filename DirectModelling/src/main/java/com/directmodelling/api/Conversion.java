package com.directmodelling.api;

public interface Conversion<Outer, Inner> {

	public abstract Inner outer2inner(Outer value) throws Exception;

	public abstract Outer inner2outer(Inner inner);

}