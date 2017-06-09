package fr.esgi.rocket.core.repository;

import org.dizitart.no2.Nitrite;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Optional;

@Component
public class NitriteConnection implements Repository<Nitrite> {
	
	private Optional<Nitrite> connection;
	
	private final String databasePath;
	private final String user;
	private final String password;
	
	public NitriteConnection(
		@Value("${info.rocket.nitrite.db}") final String database,
		@Value("${info.rocket.nitrite.user}") final String user,
		@Value("${info.rocket.nitrite.password}") final String password) {

		final String workingDir = System.getProperty("user.dir");
		
		this.connection = Optional.empty();
		this.databasePath = workingDir + File.separator + database;
		this.user = user;
		this.password = password;
		
		if(databaseExists()) {
			initDatabase();
		}
	}
	
	public final boolean databaseExists() {
		return new File(databasePath).exists();
	}
	
	public final void initDatabase() {
		this.connection = Optional.of(
			Nitrite.builder()
				.compressed()
				.filePath(databasePath)
				.openOrCreate(user, password)
		);
	}
	
	public Optional<Nitrite> getConnection() {
		return connection;
	}
}
