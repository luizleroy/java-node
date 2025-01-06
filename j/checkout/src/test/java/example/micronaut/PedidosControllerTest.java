package example.micronaut;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
class PedidosControllerTest {

    @Inject
    @Client("/examples")
    HttpClient client;

    @Test
    void testGetPedidos() {
        // Arrange
        HttpRequest<?> request = HttpRequest.GET("/");

        // Act
        HttpResponse<List> response = client.toBlocking().exchange(request, List.class);

        // Assert
        assertEquals(200, response.code());
        assertNotNull(response.body());
        assertTrue(response.body().isEmpty(), "The list should be empty initially");
    }

    @Test
    void testCreatePedidos() {
        // Arrange
        Pedido pedido = Pedido.builder().item("100").build();
        HttpRequest<Pedido> request = HttpRequest.POST("/", pedido);

        // Act
        HttpResponse<String> response = client.toBlocking().exchange(request, String.class);

        // Assert
        assertEquals(201, response.code());
        assertEquals("Pedidos added successfully", response.body());

        // Validate GET
        List<Pedido> pedidos = client.toBlocking().retrieve(HttpRequest.GET("/"), List.class);
        assertFalse(pedidos.isEmpty());
        assertEquals(1, pedidos.size());
    }

    @Test
    void testUpdateStatus() {
        // Arrange: Create a pedido first
        Pedido pedido = Pedido.builder().item("100").status(Status.PENDENTE).build();
        client.toBlocking().exchange(HttpRequest.POST("/", pedido), String.class);

        // Act: Update the pedido
        Pedido updatedPedido =Pedido.builder().item("100").status(Status.SUCESSO).build();
        HttpRequest<Pedido> request = HttpRequest.PUT("/0", updatedPedido);
        HttpResponse<String> response = client.toBlocking().exchange(request, String.class);

        // Assert
        assertEquals(200, response.code());
        assertEquals("Example updated successfully", response.body());

        // Validate GET
        List<Pedido> pedidos = client.toBlocking().retrieve(HttpRequest.GET("/"), List.class);
        assertNotNull(pedidos.get(0));
    }

    @Test
    void testDeletePedido() {
        // Arrange: Create a pedido first
        Pedido pedido = Pedido.builder().item("100").build();
        client.toBlocking().exchange(HttpRequest.POST("/", pedido), String.class);

        // Act: Delete the pedido
        HttpRequest<?> request = HttpRequest.DELETE("/0");
        HttpResponse<String> response = client.toBlocking().exchange(request, String.class);

        // Assert
        assertEquals(200, response.code());
        assertEquals("Example deleted successfully", response.body());

        // Validate GET
        List<Pedido> pedidos = client.toBlocking().retrieve(HttpRequest.GET("/"), List.class);
        assertTrue(pedidos.isEmpty());
    }

    @Test
    void testInvalidIndex() {
        // Act: Try to update a non-existent index
        Pedido pedido = Pedido.builder().item("50").build();
        HttpRequest<Pedido> request = HttpRequest.PUT("/99", pedido);
        HttpResponse<String> response = client.toBlocking().exchange(request, String.class);

        // Assert
        assertEquals(404, response.code());
        assertEquals("Index not found", response.body());
    }
}
