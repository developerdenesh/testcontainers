package com.example.demo

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.apache.http.HttpHost;

@SpringBootTest
class DemoApplicationTests {

	@Test
	fun contextLoads() {
	}

	@Test
	fun testcontainerstest() {
		val container = ElasticsearchContainer("docker.elastic.co/elasticsearch/elasticsearch:8.13.4")
						.withEnv("xpack.security.enabled", "false")

		container.start()

		val credentialsProvider = BasicCredentialsProvider().apply {
			setCredentials(
				AuthScope.ANY,
				UsernamePasswordCredentials("elastic", "changeme")
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
