package com.example.blogging_platform_api_migrated;

import com.example.blogging_platform_api_migrated.models.Post;
import com.example.blogging_platform_api_migrated.repositories.PostRepository;
import com.example.blogging_platform_api_migrated.services.PostService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BloggingPlatformApiMigratedApplicationTests {

	@Autowired
	public PostRepository postRepository;

	public static OffsetDateTime updateTime =  OffsetDateTime.of(
			2005, 06, 20, 8, 0, 0, 0,
			ZoneOffset.UTC
	);

	@BeforeAll
	void init() {
		postRepository.saveAll(generatePosts());
	}

	private List<Post> generatePosts() {
		List<Post> posts = new ArrayList<>();
		Post post1 = new Post(
				"My First Blog Post",
				"Not much to see here...",
				"Boring",
				new String[] {"Chill", "Relaxed"},
				OffsetDateTime.now(),
				OffsetDateTime.now().plusDays(2).plusHours(5).plusMinutes(39)
		);
		Post post2 = new Post(
				"My Second Blog Post",
				"Still not much to see here...",
				"More Boredom",
				new String[] {"Quiet", "Crickets"},
				OffsetDateTime.now().plusMinutes(5),
				OffsetDateTime.now().plusDays(4).plusHours(13)
		);
		Post post3 = new Post(
				"Inspirational Learning Quote",
				"Never stop learning. Because if you stop learning" +
						"You stop living",
				"Inspirational",
				new String[] {"ThinkMore", "StayAlive"},
				OffsetDateTime.now().minusHours(6),
				OffsetDateTime.now().plusMonths(3)
		);
		Post post4 = new Post(
				"A Helpful Advice",
				"If you are going camping," +
						" set your voicemail to where you are camping.",
				"Advice",
				new String[] {"KeepSafe", "Helpful"},
				OffsetDateTime.now().minusYears(5),
				OffsetDateTime.now().minusYears(5).plusHours(10)
		);
		Post post5 = new Post(
				"Tips On How To Get Hired",
				"To be more hire-able, you need to " +
						"show that companies can trust you." +
						" Build. More. Proof.",
				"Advice",
				new String[]{"Business", "JobHunting"},
				OffsetDateTime.now().minusYears(3),
				OffsetDateTime.now().minusYears(2)
		);
		posts.add(post1);
		posts.add(post2);
		posts.add(post3);
		posts.add(post4);
		posts.add(post5);

		return posts;
	}

	@AfterAll
	void tearDown() {
		postRepository.deleteAll();
	}

}
