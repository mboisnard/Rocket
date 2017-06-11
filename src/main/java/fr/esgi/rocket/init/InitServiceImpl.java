package fr.esgi.rocket.init;

import fr.esgi.rocket.core.repository.NitriteConnection;
import org.dizitart.no2.Nitrite;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
class InitServiceImpl implements InitService {
	
	private final NitriteConnection connection;
	
	InitServiceImpl(final NitriteConnection connection) {
		this.connection = connection;
	}
	
	@Override
	public void initRepository() {

		final Optional<Nitrite> nitrite = connection.getConnection();
		
		if(nitrite.isPresent()) {
			connection.closeConnection();
			throw new IllegalStateException("A Rocket repository is already initialized in this directory");
		}
		
		connection.initDatabase();
		connection.closeConnection();
	}
}
