package com.example.demo

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class DemoApplicationTests {

	@Test
	fun contextLoads() {
	}

	@Test
	fun testcontainerstest() {
		val container = ElasticsearchContainer("docker.elastic.co/elasticsearch/elasticsearch:7.15.0")

		container.start()

		val credentialsProvider = BasicCredentialsProvider().apply {
			setCredentials(
				AuthScope.ANY,
				UsernamePasswordCredentials(ELASTICSEARCH_USERNAME, ELASTICSEARCH_PASSWORD)
			)
		}

		val client = RestClient.builder(HttpHost.create(container.httpHostAddress))
			.setHttpClientConfigCallback { httpClientBuilder ->
				httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider)
			}
			.build()

		val response = client.performRequest(Request("GET", "/_cluster/health"))

		// Use the response as needed

		container.close()
		assertTrue(true);
	}
}
