package fr.esgi.rocket.core.repository;

import java.io.Serializable;
import java.util.List;

public interface QueryRepository<T extends Serializable, U extends Serializable> {
	T get(final U id);
	List<T> getAll();
}
