package com.apollographql.federation.springexample;

import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTest;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@GraphQLTest
public class AppTest {
    @Autowired
    private GraphQLTestTemplate graphqlTestTemplate;

    @Test
    public void lookupPlanckProduct() throws IOException {
        final GraphQLResponse response = graphqlTestTemplate.postForResource("queries/LookupPlanckProduct.graphql");
        assertNotNull(response, "response should not have been null");
        assertTrue(response.isOk(), "response should have been OK");
        final int quantity = response.get("$.data._entities[0].quantity", Integer.class);
        assertEquals(8658, quantity, "Inventory contains 8658 Plancks");
    }

    @Test
    public void sdlTest() throws IOException {
        final GraphQLResponse response = graphqlTestTemplate.postForResource("queries/ServiceSdlCall.graphql");
        assertNotNull(response, "response should not have been null");
        assertTrue(response.isOk(), "response should have been OK");
        System.out.println(response.get("$.data._service.sdl"));
        // final int sdl = response.get("$.data._service.sdl", String.class);
        // assertEquals("not equal", sdl, "SDL is what you expect");
    }
}
