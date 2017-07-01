package fr.esgi.rocket.init;

import fr.esgi.rocket.core.repository.NitriteConnection;
import lombok.extern.slf4j.Slf4j;
import org.dizitart.no2.Nitrite;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
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
			log.error("A Rocket repository is already initialized in this directory");
			return;
		}
		
		connection.initDatabase();
		log.info("Successfully initialized a new rocket repository!");
		connection.closeConnection();
	}
}
