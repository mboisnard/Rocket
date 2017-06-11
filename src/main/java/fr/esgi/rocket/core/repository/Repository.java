package fr.esgi.rocket.core.repository;

import java.util.Optional;

public interface Repository<T> {
	Optional<T> getConnection();
	void closeConnection();
}
