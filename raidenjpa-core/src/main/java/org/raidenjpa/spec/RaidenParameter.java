package org.raidenjpa.spec;

import javax.persistence.Parameter;

public class RaidenParameter<T> implements Parameter<T> {

	public String getName() {
		return null;
	}

	public Integer getPosition() {
		return null;
	}

	public Class<T> getParameterType() {
		return null;
	}
}
