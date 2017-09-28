package br.com.uniritter.tasima.idaEventos.integration;

import br.com.uniritter.tasima.idaEventos.IdaEventosApplication;
import br.com.uniritter.tasima.idaEventos.domain.model.Evento;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Calendar;
import java.util.Date;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

//TODO Arrumar testes unitários de acordo com a implementação da US #3 - Disponibilizar Ingressos

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = IdaEventosApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class EventoControllerIntegrationTest {
    private static RequestSpecification spec;

    @LocalServerPort
    private int port;

    @Before
    public void setup() throws Exception {
        spec = new RequestSpecBuilder().setContentType(ContentType.JSON).setAccept(ContentType.JSON).setPort(port).setBaseUri("http://localhost").build();
    }

    @Test
    public void buscarPorNome_eventoExistente_encontrado() throws ParseException, IOException {
        // dado um evento existente
        Evento dto = popularDto();
        apiPost(dto);

        // quando o evento for buscado
        Response resposta = apiGetPorNome(dto.getNome());

        // então o status code deve ser OK
        resposta.then().assertThat().statusCode(HttpStatus.SC_OK);

        String stringJson = resposta.getBody().asString();

        // Clean up
        apiDelete(stringJsonToEvento(stringJson).getIdEvento()).then().assertThat().statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void buscarPorNome_eventoInexistente_naoEncontrado() throws ParseException, JsonProcessingException {
        // dado um evento populado, mas não existente
        Evento dto = popularDto();

        // quando buscar por nome, então o status code deve ser NOT FOUND.
        apiGetPorNome(dto.getNome()).then().assertThat().statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void cadastrar_eventoCompleto_criado() throws ParseException, IOException {
        // dado um evento populado corretamente
        Evento dtoPopulado = popularDto();

        // quando cadastrar o evento, então o status code deve ser CREATED.
        apiPost(dtoPopulado).then().assertThat().statusCode(HttpStatus.SC_CREATED);

        // e o evento retornado por get deve ser igual ao que foi populado
        Evento dtoCriado = stringJsonToEvento(apiGetPorNome(dtoPopulado.getNome()).getBody().asString());
        assertEquals(dtoPopulado.getNome(), dtoCriado.getNome());

        // Clean up
        apiDelete(dtoCriado.getIdEvento()).then().assertThat().statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void cadastrar_eventoSemNome_naoCriado() throws ParseException, JsonProcessingException {
        // dado um evento populado sem nome
        Evento dto = popularDto();
        dto.setNome(null);

        // quando cadastrar o evento, então o status code deve ser BAD REQUEST
        apiPost(dto).then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void cadastrar_eventoComNomeCom150Caracteres_criado() throws ParseException, IOException {
        // dado um evento populado com nome de 150 caracteres
        Evento dtoPopulado = popularDto();
        dtoPopulado.setNome(RandomStringUtils.randomAlphabetic(150));

        // quando cadastrar o evento, então o status code deve ser CREATED
        apiPost(dtoPopulado).then().assertThat().statusCode(HttpStatus.SC_CREATED);

        // Clean up
        Evento dtoCriado = stringJsonToEvento(apiGetPorNome(dtoPopulado.getNome()).getBody().asString());
        apiDelete(dtoCriado.getIdEvento()).then().assertThat().statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void cadastrar_eventoComNomeCom151Caracteres_naoCriado() throws ParseException, JsonProcessingException {
        // dado um evento populado com nome de 151 caracteres
        Evento dto = popularDto();
        dto.setNome(RandomStringUtils.randomAlphabetic(151));

        // quando cadastrar o evento, então o status code deve ser BAD REQUEST
        apiPost(dto).then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void cadastrar_eventoSemData_naoCriado() throws ParseException, JsonProcessingException {
        // dado um evento populado sem data
        Evento dto = popularDto();
        dto.setData(null);

        // quando cadastrar o evento, então o status code deve ser BAD REQUEST
        apiPost(dto).then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void cadastrar_eventoDataPassada_naoCriado() throws ParseException, JsonProcessingException {
        // dado um evento populado com data passada
        Evento dto = popularDto();
        dto.setData(getYesterday());

        // quando cadastrar o evento, então o status code deve ser BAD REQUEST
        apiPost(dto).then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void listar_eventosExistentes_eventosEncontrados() throws ParseException, IOException {
        // dado um conjunto de eventos existentes
        Evento dto1 = popularDto();
        dto1.setNome("Evento1");
        apiPost(dto1);
        Evento dto2 = popularDto();
        dto2.setNome("Evento2");
        apiPost(dto2);
        Evento dto3 = popularDto();
        dto3.setNome("Evento3");
        apiPost(dto3);

        // quando listar os eventos
        Response resposta = apiGetAll();

        // então o status code deve ser OK
        resposta.then().assertThat().statusCode(HttpStatus.SC_OK);

        // e os eventos devem ser retornados
        assertNotEquals("[]", resposta.getBody().asString());

        // clean up
        apiDelete(stringJsonToEvento(apiGetPorNome(dto1.getNome()).getBody().asString()).getIdEvento());
        apiDelete(stringJsonToEvento(apiGetPorNome(dto2.getNome()).getBody().asString()).getIdEvento());
        apiDelete(stringJsonToEvento(apiGetPorNome(dto3.getNome()).getBody().asString()).getIdEvento());
    }

    @Test
    public void listar_nenhumEventoExistente_nenhumEventoEncontrado() throws ParseException, JsonProcessingException {
        // dado que não existe nenhum evento

        // quando listar os eventos
        Response resposta = apiGetAll();

        // então o status code deve ser OK
        resposta.then().assertThat().statusCode(HttpStatus.SC_OK);

        // e nenhum evento deve ser retornado
        assertEquals("[]", resposta.getBody().asString());
    }

    @Test
    public void buscarPorId_eventoExistente_eventoEncontrado() throws IOException {
        // dado um evento existente
        Evento dto = popularDto();
        apiPost(dto);

        // quando o evento for buscado
        Response resposta = apiGetPorId(stringJsonToEvento(apiGetPorNome(dto.getNome()).getBody().asString()).getIdEvento());

        // então o status code deve ser OK
        resposta.then().assertThat().statusCode(HttpStatus.SC_OK);

        String stringJson = resposta.getBody().asString();

        // Clean up
        apiDelete(stringJsonToEvento(stringJson).getIdEvento()).then().assertThat().statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void buscarPorId_eventoInexistente_eventoNaoEncontrado() throws IOException {
        // dado que não existe nenhum evento

        // quando o evento for buscado
        Response resposta = apiGetPorId(1L);

        // então o status code deve ser NOT FOUND
        resposta.then().assertThat().statusCode(HttpStatus.SC_NOT_FOUND);
    }

    private Response apiPost(Evento dto) {
        return given().spec(spec).body(dto, ObjectMapperType.JACKSON_2).when().post("/api/evento/cadastrar");
    }

    private Response apiDelete(long id) {
        return given().spec(spec).delete("/api/evento/deletar/" + id);
    }

    private Response apiGetPorId(long id) {
        return given().spec(spec).get("/api/evento/id/" + id);
    }

    private Response apiGetPorNome(String nome) {
        return given().spec(spec).get("/api/evento/buscar/" + nome);
    }

    private Response apiGetAll() {
        return given().spec(spec).get("/api/evento/listar");
    }

    private Evento popularDto() {
        Evento evento = new Evento();

        evento.setNome(RandomStringUtils.randomAlphabetic(20));
        evento.setData(getTomorrow());

        return evento;
    }

    private Evento stringJsonToEvento(String stringJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(stringJson, Evento.class);
    }

    private Date getTomorrow() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        return cal.getTime();
    }

    private Date getYesterday() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, - 1);
        return cal.getTime();
    }
}
