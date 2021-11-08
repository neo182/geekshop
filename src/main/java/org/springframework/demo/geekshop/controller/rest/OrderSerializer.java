package org.springframework.demo.geekshop.controller.rest;

import java.io.IOException;

import org.springframework.demo.geekshop.domain.Order;
import org.springframework.demo.geekshop.domain.OrderItem;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class OrderSerializer extends StdSerializer<Order> {
    public OrderSerializer() {
        super(Order.class);
    }

    protected OrderSerializer(final Class<Order> t) {
        super(t);
    }

    @Override
    public void serialize(final Order order, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        //order specific details
        jsonGenerator.writeStringField("status", order.getStatus());
        jsonGenerator.writeStringField("orderedDate", order.getOrderedDate().toString());
        jsonGenerator.writeStringField("totalSum", order.getTotalSum().toString());
        jsonGenerator.writeStringField("shippingCost", order.getShippingCost().toString());
        jsonGenerator.writeStringField("paymentMethod", order.getPaymentMethod());

        jsonGenerator.writeStringField("receiversFirstName", order.getReceiversFirstName());
        jsonGenerator.writeStringField("receiversLastName", order.getReceiversLastName());
        jsonGenerator.writeStringField("receiversContactNumber", order.getReceiversContactNumber());
        jsonGenerator.writeStringField("receiversEmail", order.getReceiversEmail());
        jsonGenerator.writeStringField("city", order.getCity());
        jsonGenerator.writeStringField("street", order.getStreet());
        jsonGenerator.writeStringField("country", order.getCountry());
        jsonGenerator.writeStringField("postcode", order.getPostcode());

        jsonGenerator.writeFieldName("orderItems");
        jsonGenerator.writeStartArray();
        for (OrderItem orderItem : order.getItems()) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("orderItemId", orderItem.getId().toString());
            jsonGenerator.writeStringField("itemId", orderItem.getCatalogItem().getId().toString());
            jsonGenerator.writeStringField("name", orderItem.getCatalogItem().getName());
            jsonGenerator.writeStringField("soldPrice", orderItem.getSoldPrice().toString());
            jsonGenerator.writeStringField("quantity", orderItem.getQuantity().toString());
            jsonGenerator.writeStringField("description", orderItem.getCatalogItem().getDescription());
            jsonGenerator.writeStringField("pictureFileName", orderItem.getCatalogItem().getPictureFileName());
            jsonGenerator.writeStringField("pictureUrl", orderItem.getCatalogItem().getPictureUrl());
            jsonGenerator.writeStringField("description", orderItem.getCatalogItem().getDescription());
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
    }
}
