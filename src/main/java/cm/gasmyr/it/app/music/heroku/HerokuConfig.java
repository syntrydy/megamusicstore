package cm.gasmyr.it.app.music.heroku;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class HerokuConfig {
	@Bean
	public javax.sql.DataSource dataSource() throws URISyntaxException {
		URI dbUri = new URI(System.getenv("DATABASE_URL"));
		String username = dbUri.getUserInfo().split(":")[0];
		String password = dbUri.getUserInfo().split(":")[1];
		String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();
		return DataSourceBuilder.create().username(username).password(password).url(dbUrl).build();
	}
}
