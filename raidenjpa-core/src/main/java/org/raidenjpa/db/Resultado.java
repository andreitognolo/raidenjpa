package org.raidenjpa.db;

import java.util.List;

import org.raidenjpa.util.FixMe;

@FixMe("Verify")
public interface Resultado<T> {

	public List<T> asList();

	public Iterable<T> asIterable();

}
