package example.micronaut;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public class Pedido {
    private String id;
    private String item;
    private Status status;
    private BigDecimal preco;
    private Integer quantidade;
}
