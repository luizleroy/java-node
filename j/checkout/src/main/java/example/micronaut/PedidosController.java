package example.micronaut;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

import java.util.ArrayList;
import java.util.List;

@Controller("/examples")
public class HelloController {

    private final List<Pedido> examples = new ArrayList<>();

    @Get
    @Operation(summary = "Retrieve all Pedidos", description = "Returns a list of all examples")
    public List<Pedido> getPedidos() {
        return examples;
    }

    @Post
    @Operation(summary = "Create a new pedido", description = "Adds a new pedido to the list")
    public MutableHttpResponse<String> createPedidos(@Body Pedido pedido) {
        examples.add(pedido);
        return HttpResponse.created("Pedidos added successfully");
    }

    @Put("/{index}")
    @Operation(summary = "Update an Pedido", description = "Updates an pedidos at the specified index")
    public MutableHttpResponse<String> updatePedido(@PathVariable int index, @Body Pedido pedido) {
        if (index >= 0 && index < examples.size()) {
            examples.set(index, pedido);
            return HttpResponse.ok("Example updated successfully");
        } else {
            return HttpResponse.notFound("Index not found");
        }
    }

    @Delete("/{index}")
    @Operation(summary = "Delete an example", description = "Deletes an example at the specified index")
    public HttpResponse<String> deletePedido(@PathVariable int index) {
        if (index >= 0 && index < examples.size()) {
            examples.remove(index);
            return HttpResponse.ok("Example deleted successfully");
        } else {
            return HttpResponse.notFound("Index not found");
        }
    }
}
