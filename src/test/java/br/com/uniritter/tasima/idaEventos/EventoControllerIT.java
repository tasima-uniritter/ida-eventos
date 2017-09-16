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

//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
public class EventoControllerIT {
    private static RequestSpecification spec;

    @BeforeClass
    public static void setup() throws Exception {
        System.setProperty("enviroment", "test");

        spec = new RequestSpecBuilder().setContentType(ContentType.JSON).setAccept(ContentType.JSON).setBaseUri("http://localhost:8080/").build();
    }

    @Test
    public void buscarPorNome_eventoExistente_encontrado() throws ParseException, JsonProcessingException {
        // dado um evento existente
        Evento dto = popularDto();
        apiPost(dto);

        // quando o evento for buscado, então o status code deve ser OK
        apiGetPorNome(dto.getNome()).then().assertThat().statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void buscarPorNome_eventoInexistente_naoEncontrado() throws ParseException, JsonProcessingException {
        // dado um evento populado, mas não existente
        Evento dto = popularDto();

        // quando buscar por nome, então o status code deve ser NOT FOUND.
        apiGetPorNome(dto.getNome()).then().assertThat().statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void cadastrar_eventoCompleto_criado() throws ParseException, JsonProcessingException {
        // dado um evento populado corretamente
        Evento dto = popularDto();

        // quando cadastrar o evento, então o status code deve ser CREATED.
        apiPost(dto).then().assertThat().statusCode(HttpStatus.SC_CREATED);

        // e o evento retornado por get deve ser igual ao que foi cadastrado
        assertEqualsDTO(dto, apiGetPorNome(dto.getNome()));

        // Clean up
        apiDelete(dto.getIdEvento());
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
    public void cadastrar_eventoComNomeCom150Caracteres_criado() throws ParseException, JsonProcessingException {
        // dado um evento populado com nome de 150 caracteres
        Evento dto = popularDto();
        dto.setNome("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum pulvinar mi vitae elit dapibus aliquam. In vitae purus eu dolor molestie cras amet");

        // quando cadastrar o evento, então o status code deve ser CREATED
        apiPost(dto).then().assertThat().statusCode(HttpStatus.SC_CREATED);

        // Clean up
        apiDelete(dto.getIdEvento());
    }

    @Test
    public void cadastrar_eventoComNomeCom151Caracteres_naoCriado() throws ParseException, JsonProcessingException {
        // dado um evento populado com nome de 151 caracteres
        Evento dto = popularDto();
        dto.setNome("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum pulvinar mi vitae elit dapibus aliquam. In vitae purus eu dolor molestie cras amet.");

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
    public void listar_eventosExistentes_eventosEncontrados() throws ParseException, JsonProcessingException {
        // dado um conjunto de eventos existentes
        Evento dto1 = popularDto();
        dto1.setNome("Evento1");
        Evento dto2 = popularDto();
        dto2.setNome("Evento2");
        Evento dto3 = popularDto();
        dto3.setNome("Evento3");

        // quando listar os eventos
        Response resposta = apiGetAll();

        // então o status code deve ser OK
        resposta.then().assertThat().statusCode(HttpStatus.SC_OK);

        // e os eventos devem ser retornados
        //TODO: verificar como será feito esse teste
    }

    @Test
    public void listar_nenhumEventoExistente_nenhumEventoEncontrado() throws ParseException, JsonProcessingException {
        // dado que não existe nenhum evento

        // quando listar os eventos
        Response resposta = apiGetAll();

        // então o status code deve ser OK
        resposta.then().assertThat().statusCode(HttpStatus.SC_OK);

        // e nenhum evento deve ser retornado
        //TODO: verificar como será feito esse teste
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

    private void assertEqualsDTO(Evento dtoExpected, Response responseActual) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String stringExpectedJson = mapper.writeValueAsString(dtoExpected);

        assertEquals(stringExpectedJson, responseActual.getBody().asString());
    }

    private Evento popularDto() {
        Evento evento = new Evento();

        evento.setNome("Meu evento");
        evento.setData(getTomorrow());

        return evento;
    }

//    private JSONObject stringToJson(String stringJson) throws ParseException {
//        JSONParser parser = new JSONParser();
//        JSONObject jsonPayload = (JSONObject) parser.parse(stringJson);
//        return jsonPayload;
//    }

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
