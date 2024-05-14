package com.example.demo

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.http.Request
import org.apache.http.HttpHost // Import HttpHost from Apache HttpClient
import org.apache.http.client.CredentialsProvider // Import CredentialsProvider from Apache HttpClient
import org.apache.http.impl.client.BasicCredentialsProvider // Import BasicCredentialsProvider from Apache HttpClient
import org.apache.http.auth.AuthScope // Import AuthScope from Apache HttpClient
import org.apache.http.auth.UsernamePasswordCredentials // Import UsernamePasswordCredentials from Apache HttpClient
import org.apache.http.client.ResponseHandler // Import ResponseHandler from Apache HttpClient
import org.apache.http.client.methods.Request // Import Request from Apache HttpClient
import org.apache.http.impl.client.HttpClients // Import HttpClients from Apache HttpClient
import org.apache.http.impl.nio.client.HttpAsyncClients // Import HttpAsyncClients from Apache HttpClient
import org.apache.http.util.EntityUtils // Import EntityUtils from Apache HttpClient

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
