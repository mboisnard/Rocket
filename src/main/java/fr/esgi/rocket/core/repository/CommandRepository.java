package fr.esgi.rocket.core.repository;

import java.io.Serializable;

public interface CommandRepository<T extends Serializable> {
	T save(final T entity);
	T update(final T entity);
	T delete(final T entity);
}
