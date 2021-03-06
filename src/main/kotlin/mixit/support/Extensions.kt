package mixit.support

import mixit.model.User
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.support.EncodedResource
import org.springframework.core.io.support.ResourcePropertySource
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.repository.query.MongoEntityInformation
import org.springframework.data.mongodb.repository.support.ReactiveMongoRepositoryFactory
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.ClientRequest
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.io.Serializable
import java.nio.charset.StandardCharsets
import kotlin.reflect.KClass


fun ConfigurableEnvironment.addPropertySource(location: String) {
    propertySources.addFirst(ResourcePropertySource(EncodedResource(ClassPathResource(location), StandardCharsets.UTF_8)))
}

inline fun <reified T : Any> ReactiveMongoTemplate.findById(id: Any) : Mono<T> = findById(id, T::class.java)

inline fun <reified T : Any> ReactiveMongoTemplate.find(query: Query) : Flux<T> = find(query, T::class.java)

fun <T : Any, ID : Serializable> ReactiveMongoRepositoryFactory.getEntityInformation(type: KClass<T>) : MongoEntityInformation<T, ID> = getEntityInformation(type.java)

fun WebClient.getJson(url: String) = exchange(ClientRequest.GET(url).accept(MediaType.APPLICATION_JSON_UTF8).build())

fun WebClient.getHtml(url: String) = exchange(ClientRequest.GET(url).accept(MediaType.TEXT_HTML).build())

fun WebClient.postJson(url: String, body: Any) = exchange(ClientRequest.POST(url).accept(MediaType.APPLICATION_JSON_UTF8).body(BodyInserters.fromObject(body)))