package br.com.uniritter.tasima.idaEventos;

import static org.junit.Assert.assertEquals;
import br.com.uniritter.tasima.idaEventos.domain.model.Evento;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import io.restassured.response.Response;

import java.text.ParseException;

import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
public class EventoControllerIT {
    private static RequestSpecification spec;

    @BeforeClass
    public static void setup() throws Exception {
        System.setProperty("enviroment", "test");

        spec = new RequestSpecBuilder().setContentType(ContentType.JSON).setAccept(ContentType.JSON).setBaseUri("http://localhost:8080/").build();
    }

    @Ignore
    @Test
    public void get_ExistingScheduler_success() throws ParseException, JsonProcessingException {
        // given a created scheduler
        Evento dto = popularDto();
        apiPost(dto);

        // when get the scheduler, then status code should be OK.
        apiGetPorId(dto.getIdEvento()).then().assertThat().statusCode(HttpStatus.SC_OK);

        // Clean up
        apiDelete(dto.getIdEvento());
    }

    @Ignore
    @Test
    public void get_NonexistingScheduler_fail() throws ParseException, JsonProcessingException {
        // given a inexistent scheduler
        Evento dto = popularDto();

        // when get the scheduler, then status code should be NOT FOUND.
        apiGetPorId(dto.getIdEvento()).then().assertThat().statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Ignore
    @Test
    public void post_evento_success() throws ParseException, JsonProcessingException {
        // given a created scheduler
        Evento dto = popularDto();
        apiPost(dto).then().assertThat().statusCode(HttpStatus.SC_CREATED);
    }

    private Response apiPost(Evento dto) {
        return given().spec(spec).body(dto, ObjectMapperType.JACKSON_2).when().post("/evento/cadastrar");
    }

    private Response apiPut(long id, Evento dto) {
        return given().spec(spec).body(dto, ObjectMapperType.JACKSON_2).when().put("/evento/atualizar/" + id);
    }

    private Response apiDelete(long id) {
        return given().spec(spec).delete("/evento/deletar/" + id);
    }

    private Response apiGetPorId(long id) {
        return given().spec(spec).get("/evento/id/" + id);
    }

    private Response apiGetPorNome(String nome) {
        return given().spec(spec).get("/evento/buscar/" + nome);
    }

    private Response apiGetAll() {
        return given().spec(spec).get("/evento/listar");
    }

    private void assertEqualsDTO(Evento dtoExpected, Response responseActual) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String stringExpectedJson = mapper.writeValueAsString(dtoExpected);

        assertEquals(stringExpectedJson, responseActual.getBody().asString());
    }

    private Evento popularDto() {
        Evento evento = new Evento();

        evento.setIdEvento(123L);
        evento.setNome("Fulano da Silva");

        return evento;
    }
}
