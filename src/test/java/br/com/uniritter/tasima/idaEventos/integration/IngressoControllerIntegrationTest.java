package br.com.uniritter.tasima.idaEventos.integration;

import br.com.uniritter.tasima.idaEventos.IdaIngressosApplication;
import br.com.uniritter.tasima.idaEventos.domain.model.Ingresso;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.swagger.models.auth.In;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.text.ParseException;


import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringRunner.class)

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = IdaIngressosApplication.class)

@AutoConfigureMockMvc

@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class IngressoControllerIntegrationTest {
    private static RequestSpecification spec;

    @LocalServerPort
    private int port;

    @Before
    public void setup() throws Exception {
        spec = new RequestSpecBuilder().setContentType(ContentType.JSON).setAccept(ContentType.JSON).setPort(port).setBaseUri("http://localhost").build();
    }

    @Test
    public void buscarPorTipo_ingressoExistente_encontrado() throws ParseException, IOException {
        // dado um tipo de ingresso existente
        Ingresso dto = popularDto();
        Response response = apiPost(dto);

        // quando o tipo de ingresso for buscado
        Response resposta = apiGetPorTipo(dto.getTipo());

        // então o status code deve ser OK
        resposta.then().assertThat().statusCode(HttpStatus.SC_OK);

        String stringJson = resposta.getBody().asString();

        // Clean up
        apiDelete(stringJsonToEvento(stringJson).getIdIngresso()).then().assertThat().statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void buscarPorTipo_ingressoInexistente_naoEncontrado() throws ParseException, JsonProcessingException {
        // dado um ingresso populado, mas não existente
        Ingresso dto = popularDto();

        // quando buscar por tipo, então o status code deve ser NOT FOUND.
        apiGetPorTipo(dto.getTipo()).then().assertThat().statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void cadastrar_ingressoCompleto_criado() throws ParseException, IOException {
        // dado um ingresso populado corretamente
        Ingresso dtoPopulado = popularDto();

        // quando cadastrar o ingresso, então o status code deve ser CREATED.
        apiPost(dtoPopulado).then().assertThat().statusCode(HttpStatus.SC_CREATED);

        // e o ingresso retornado por get deve ser igual ao que foi populado
        Ingresso dtoCriado = stringJsonToEvento(apiGetPorTipo(dtoPopulado.getTipo()).getBody().asString());
        assertEquals(dtoPopulado.getTipo(), dtoCriado.getTipo());

        // Clean up
        apiDelete(dtoCriado.getIdIngresso()).then().assertThat().statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void cadastrar_ingressoSemTipo_naoCriado() throws ParseException, JsonProcessingException {
        // dado um ingresso populado sem tipo e valor
        Ingresso dto = popularDto();
        dto.setTipo(null);
        dto.setValor(1000.0);

        // quando cadastrar o ingresso, então o status code deve ser BAD REQUEST
        apiPost(dto).then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void listar_ingressosExistentes_ingressosEncontrados() throws ParseException, IOException {
        // dado um conjunto de ingressos existentes
        Ingresso dto1 = popularDto();
        dto1.setTipo("VIP");
        dto1.setValor(1000.0);
        apiPost(dto1);
        Ingresso dto2 = popularDto();
        dto2.setTipo("BACKSTAGE");
        dto2.setValor(800.0);
        apiPost(dto2);
        Ingresso dto3 = popularDto();
        dto3.setTipo("PLATEIA VIP");
        dto3.setValor(500.0);
        apiPost(dto3);
        Ingresso dto4 = popularDto();
        dto4.setTipo("PLATEIA");
        dto4.setValor(300.0);
        apiPost(dto4);

        // quando listar os ingressos
        Response resposta = apiGetAll();

        // então o status code deve ser OK
        resposta.then().assertThat().statusCode(HttpStatus.SC_OK);

        // e os ingressos devem ser retornados
        assertNotEquals("[]", resposta.getBody().asString());

        // clean up
        apiDelete(stringJsonToEvento(apiGetPorTipo(dto1.getTipo()).getBody().asString()).getIdIngresso());
        apiDelete(stringJsonToEvento(apiGetPorTipo(dto2.getTipo()).getBody().asString()).getIdIngresso());
        apiDelete(stringJsonToEvento(apiGetPorTipo(dto3.getTipo()).getBody().asString()).getIdIngresso());
        apiDelete(stringJsonToEvento(apiGetPorTipo(dto4.getTipo()).getBody().asString()).getIdIngresso());
    }

    @Test
    public void listar_nenhumIngressoExistente_nenhumIngressoEncontrado() throws ParseException, JsonProcessingException {
        // dado que não existe nenhum ingresso

        // quando listar os ingressos
        Response resposta = apiGetAll();

        // então o status code deve ser OK
        resposta.then().assertThat().statusCode(HttpStatus.SC_OK);

        // e nenhum ingresso deve ser retornado
        assertEquals("[]", resposta.getBody().asString());
    }

    @Test
    public void buscarPorId_ingressoExistente_ingressoEncontrado() throws IOException {
        // dado um ingresso existente
        Ingresso dto = popularDto();
        apiPost(dto);

        // quando o ingresso for buscado
        Response resposta = apiGetPorId(stringJsonToEvento(apiGetPorTipo(dto.getTipo()).getBody().asString()).getIdIngresso());

        // então o status code deve ser OK
        resposta.then().assertThat().statusCode(HttpStatus.SC_OK);

        String stringJson = resposta.getBody().asString();

        // Clean up
        apiDelete(stringJsonToEvento(stringJson).getIdIngresso()).then().assertThat().statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void buscarPorId_ingressoInexistente_ingressoNaoEncontrado() throws IOException {
        // dado que não existe nenhum ingresso

        // quando o ingresso for buscado
        Response resposta = apiGetPorId(1L);

        // então o status code deve ser NOT FOUND
        resposta.then().assertThat().statusCode(HttpStatus.SC_NOT_FOUND);
    }

    private Response apiPost(Ingresso dto) {
        return given().spec(spec).body(dto, ObjectMapperType.JACKSON_2).when().post("/api/ingresso/cadastrar");
    }

    private Response apiDelete(long id) {
        return given().spec(spec).delete("/api/ingresso/deletar/" + id);
    }

    private Response apiGetPorId(long id) {
        return given().spec(spec).get("/api/ingresso/id/" + id);
    }

    private Response apiGetPorTipo(String tipo) {
        return given().spec(spec).get("/api/ingresso/buscar/" + tipo);
    }

    private Response apiGetAll() {
        return given().spec(spec).get("/api/ingresso/listar");
    }

    private Ingresso popularDto() {
        Ingresso ingresso = new Ingresso();

        ingresso.setTipo("TEST");
        ingresso.setValor(5000.0);

        return ingresso;
    }

    private Ingresso stringJsonToEvento(String stringJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(stringJson, Ingresso.class);
    }


}