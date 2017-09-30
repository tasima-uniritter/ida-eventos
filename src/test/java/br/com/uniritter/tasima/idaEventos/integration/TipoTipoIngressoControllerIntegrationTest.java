package br.com.uniritter.tasima.idaEventos.integration;

import br.com.uniritter.tasima.idaEventos.IdaEventosApplication;
import br.com.uniritter.tasima.idaEventos.domain.model.TipoIngresso;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.RandomStringUtils;
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
import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = IdaEventosApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class TipoTipoIngressoControllerIntegrationTest {
    private static RequestSpecification spec;

    private static String TIPO_INGRESSO_VIP = "VIP";
    private static String TIPO_INGRESSO_BACKSTAGE = "BACKSTAGE";
    private static String TIPO_INGRESSO_PLATEIA_VIP = "PLATEIA VIP";
    private static String TIPO_INGRESSO_PLATEIA = "PLATEIA";

    @LocalServerPort
    private int port;

    @Before
    public void setup() throws Exception {
        spec = new RequestSpecBuilder().setContentType(ContentType.JSON).setAccept(ContentType.JSON).setPort(port).setBaseUri("http://localhost").build();
    }

    @Test
    public void buscarPorTipo_tipoIngressoExistente_encontrado() throws ParseException, IOException {
        // dado um tipo de ingresso existente
        TipoIngresso dto = popularDto();
        Response response = apiPost(dto);

        // quando o tipo de ingresso for buscado
        Response resposta = apiGetPorTipo(dto.getTipo());

        // então o status code deve ser OK
        resposta.then().assertThat().statusCode(HttpStatus.SC_OK);

        String stringJson = resposta.getBody().asString();

        // Clean up
        apiDelete(stringJsonToTipoIngresso(stringJson).getIdTipoIngresso()).then().assertThat().statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void buscarPorTipo_tipoIngressoInexistente_naoEncontrado() throws ParseException, JsonProcessingException {
        // dado um tipo ingresso populado, mas não existente
        TipoIngresso dto = popularDto();

        // quando buscar por tipo, então o status code deve ser NOT FOUND.
        apiGetPorTipo(dto.getTipo()).then().assertThat().statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void cadastrar_tipoIngressoCompleto_criado() throws ParseException, IOException {
        // dado um tipo ingresso populado corretamente
        TipoIngresso dtoPopulado = popularDto();

        // quando cadastrar o tipo ingresso, então o status code deve ser CREATED.
        apiPost(dtoPopulado).then().assertThat().statusCode(HttpStatus.SC_CREATED);

        // e o tipo ingresso retornado por get deve ser igual ao que foi populado
        TipoIngresso dtoCriado = stringJsonToTipoIngresso(apiGetPorTipo(dtoPopulado.getTipo()).getBody().asString());
        assertEquals(dtoPopulado.getTipo(), dtoCriado.getTipo());

        // Clean up
        apiDelete(dtoCriado.getIdTipoIngresso()).then().assertThat().statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void cadastrar_tipoIngressoSemTipo_naoCriado() throws ParseException, JsonProcessingException {
        // dado um tipo ingresso populado sem tipo e valor
        TipoIngresso dto = popularDto();
        dto.setTipo(null);

        // quando cadastrar o tipo ingresso, então o status code deve ser INTERNAL SERVER ERROR
        apiPost(dto).then().assertThat().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    public void listar_tipoIngressosExistentes_tiposIngressoEncontrados() throws ParseException, IOException {
        // dado um conjunto de tipos de ingresso existentes
        TipoIngresso dto1 = popularDto();
        dto1.setTipo("INGRESSO 1");
        dto1.setValor(1000.0);
        apiPost(dto1);
        TipoIngresso dto2 = popularDto();
        dto2.setTipo("INGRESSO 2");
        dto2.setValor(800.0);
        apiPost(dto2);
        TipoIngresso dto3 = popularDto();
        dto3.setTipo("INGRESSO 3");
        dto3.setValor(500.0);
        apiPost(dto3);
        TipoIngresso dto4 = popularDto();
        dto4.setTipo("INGRESSO 4");
        dto4.setValor(300.0);
        apiPost(dto4);

        // quando listar os tipos de ingresso
        Response resposta = apiGetAll();

        // então o status code deve ser OK
        resposta.then().assertThat().statusCode(HttpStatus.SC_OK);

        // e os tipos de ingresso devem ser retornados
        assertNotEquals("[]", resposta.getBody().asString());

        // clean up
        apiDelete(stringJsonToTipoIngresso(apiGetPorTipo(dto1.getTipo()).getBody().asString()).getIdTipoIngresso());
        apiDelete(stringJsonToTipoIngresso(apiGetPorTipo(dto2.getTipo()).getBody().asString()).getIdTipoIngresso());
        apiDelete(stringJsonToTipoIngresso(apiGetPorTipo(dto3.getTipo()).getBody().asString()).getIdTipoIngresso());
        apiDelete(stringJsonToTipoIngresso(apiGetPorTipo(dto4.getTipo()).getBody().asString()).getIdTipoIngresso());
    }

    @Test
    public void listar_tipoIngressoPadraoExistente_tiposIngressoEncontrados() throws ParseException, IOException {
        // dado que existem tipos de ingresso padrão
        Set<TipoIngresso> tiposIngressoPadrao = Sets.newHashSet(
                new TipoIngresso(1L, TIPO_INGRESSO_VIP, 1000D),
                new TipoIngresso(2L, TIPO_INGRESSO_BACKSTAGE, 800D),
                new TipoIngresso(3L, TIPO_INGRESSO_PLATEIA_VIP, 500D),
                new TipoIngresso(4L, TIPO_INGRESSO_PLATEIA, 300D)
        );

        // quando listar os tipos de ingressos padrão do sistema
        Response resposta = apiGetAll();

        // então o status code deve ser OK
        resposta.then().assertThat().statusCode(HttpStatus.SC_OK);

        // e os tipos de ingresso retornados devem ser iguais ao set padrão
        assertEquals(tiposIngressoPadrao, stringJsonToSetTipoIngresso(resposta.getBody().asString()));
    }

    @Test
    public void buscarPorId_tiposIngressoExistente_tipoIngressoEncontrado() throws IOException {
        // dado um tipo ingresso existente
        TipoIngresso dto = popularDto();
        apiPost(dto);

        // quando o tipo ingresso for buscado
        Response resposta = apiGetPorId(stringJsonToTipoIngresso(apiGetPorTipo(dto.getTipo()).getBody().asString()).getIdTipoIngresso());

        // então o status code deve ser OK
        resposta.then().assertThat().statusCode(HttpStatus.SC_OK);

        String stringJson = resposta.getBody().asString();

        // Clean up
        apiDelete(stringJsonToTipoIngresso(stringJson).getIdTipoIngresso()).then().assertThat().statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void buscarPorId_tipoIngressoInexistente_tipoIngressoNaoEncontrado() throws IOException {
        // dado que não existe nenhum tipo ingresso

        // quando o tipo ingresso for buscado
        Response resposta = apiGetPorId(1000L);

        // então o status code deve ser NOT FOUND
        resposta.then().assertThat().statusCode(HttpStatus.SC_NOT_FOUND);
    }

    private Response apiPost(TipoIngresso dto) {
        return given().spec(spec).body(dto, ObjectMapperType.JACKSON_2).when().post("/ida-eventos-api/tipoIngresso/cadastrar");
    }

    private Response apiDelete(long id) {
        return given().spec(spec).delete("/ida-eventos-api/tipoIngresso/deletar/" + id);
    }

    private Response apiGetPorId(long id) {
        return given().spec(spec).get("/ida-eventos-api/tipoIngresso/id/" + id);
    }

    private Response apiGetPorTipo(String tipo) {
        return given().spec(spec).get("/ida-eventos-api/tipoIngresso/buscar/" + tipo);
    }

    private Response apiGetAll() {
        return given().spec(spec).get("/ida-eventos-api/tipoIngresso/listar");
    }

    private TipoIngresso popularDto() {
        TipoIngresso tipoIngresso = new TipoIngresso();

        tipoIngresso.setTipo(RandomStringUtils.randomAlphabetic(20));
        tipoIngresso.setValor(Math.random());

        return tipoIngresso;
    }

    private TipoIngresso stringJsonToTipoIngresso(String stringJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(stringJson, TipoIngresso.class);
    }

    private Set<TipoIngresso> stringJsonToSetTipoIngresso(String stringJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(stringJson, new TypeReference<Set<TipoIngresso>>() {});
    }
}