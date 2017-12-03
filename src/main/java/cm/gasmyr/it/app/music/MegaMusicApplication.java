package cm.gasmyr.it.app.music;

import java.util.stream.Stream;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import cm.gasmyr.it.app.music.core.Music;
import cm.gasmyr.it.app.music.service.MusicService;

@SpringBootApplication
public class MegaMusicApplication {
	private static final String DECSRIPTION3 = "During his career Jackson has sold over 30 million albums worldwide and won several awards, including a Grammy Award, thirteen Billboard Music Awards, six World Music Awards, three American Music Awards";
	private static final String DESCRIPTION2 = "Rihanna is one of the best-selling artists of all time. Rihanna is the youngest and fastest solo artist to earn fourteen number-one singles on the Billboard Hot 100, as well as a current total of 31 Top 10 Hits on the same chart. ";
	private static final String DESCRIPTION = "Hallyday's father, Léon Smet (1908–1989), was Belgian; his mother, Huguette Clerc (1920–2007), was French. Born in Paris, Hallyday took his stage name from his aunt Desta's husband and dance partner Lee Hallyday";
	@Autowired
	private MusicService myMusicService;

	public static void main(String[] args) {
		SpringApplication.run(MegaMusicApplication.class, args);
	}

	private void populateDataBase() {
		if (myMusicService.listAll().size() < 10) {
			Stream.iterate(0, i -> i + 1).limit(75).forEach(i -> {
				Music music = Music.builder().named("LOVE FOREVER AND FOR EVERY BODY").withDescription(DESCRIPTION)
						.by("JONNT ALIDAY").build();
				music.setLikes(RandomUtils.nextInt(100, 500));
				music.setDislikes(RandomUtils.nextInt(10, 50));
				setFileName(music);
				myMusicService.saveOrUpdate(music);
			});
			Stream.iterate(0, i -> i + 1).limit(75).forEach(i -> {
				Music music = Music.builder().named("GET RICH OR DIE TRYIN").withDescription(DESCRIPTION2)
						.by("RIHANNA ROBYN").build();
				music.setLikes(RandomUtils.nextInt(100, 500));
				setFileName(music);
				music.setDislikes(RandomUtils.nextInt(10, 50));
				myMusicService.saveOrUpdate(music);
			});
			Stream.iterate(0, i -> i + 1).limit(50).forEach(i -> {
				Music music = Music.builder().named("GOOD GIRL GONE BAD AND THEN").withDescription(DECSRIPTION3)
						.by("5O CENT").build();
				music.setLikes(RandomUtils.nextInt(100, 500));
				setFileName(music);
				music.setDislikes(RandomUtils.nextInt(10, 50));
				myMusicService.saveOrUpdate(music);
			});
		}
	}

	private void setFileName(Music music) {
		int rand = RandomUtils.nextInt(1, 4);
		if (rand == 1) {
			music.setFileName("sample.mp4");
		}
		if (rand == 2) {
			music.setFileName("sample.mp3");
		}
		if (rand == 3) {
			music.setFileName("sample.jpeg");
		}
	}

	@Component
	public class CommandLineAppStartupRunner implements CommandLineRunner {
		@Override
		public void run(String... args) throws Exception {

			populateDataBase();
		}
	}
}
