package integration.technology.socha.chris.smartmirror.travel.gateways;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import technology.socha.chris.smartmirror.travel.gateways.TflGateway;
import technology.socha.chris.smartmirror.travel.models.TubeLine;
import technology.socha.chris.smartmirror.travel.models.TubeStatus;
import utils.categories.Integration;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.assertThat;

@Category(Integration.class)
public class TflGatewayTest {

    private static final String VALID_APP_ID = "appId";
    private static final String VALID_APP_KEY = "appKey";
    private static final String INVALID_AUTH = "appKey";
    private static String validResponse;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule();
    private WebTarget wireMockTarget;

    @BeforeClass
    public static void before() throws Exception {
        validResponse = IOUtils.toString(TflGateway.class.getResourceAsStream("/valid-status-response.json"));
    }

    @Before
    public void beforeEach(){
        JerseyClient client = JerseyClientBuilder.createClient();

        wireMockTarget = client.target("http://localhost:" + wireMockRule.port());

        stubFor(get(urlPathEqualTo("/tube/Status"))
                .withQueryParam("app_id", matching(VALID_APP_ID))
                .withQueryParam("app_key", matching(VALID_APP_KEY))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON)
                        .withBody(validResponse)));

        stubFor(get(urlPathEqualTo("/tube/Status"))
                .withQueryParam("app_id", notMatching(VALID_APP_ID))
                .withQueryParam("app_key", matching(VALID_APP_KEY))
                .willReturn(aResponse()
                        .withStatus(401)));
    }

    @Test
    public void shouldReturnStatus() throws Exception {
        TflGateway tflGateway = new TflGateway(wireMockTarget, VALID_APP_ID, VALID_APP_KEY);

        List<TubeLine> tubeStatus = tflGateway.getStatus("tube");

        TubeStatus goodStatus = new TubeStatus(10, "Good Service", null);
        TubeLine bakerlooLine = new TubeLine("Bakerloo", newArrayList(goodStatus));
        assertThat(tubeStatus).contains(bakerlooLine);
    }

    @Test(expected = NotAuthorizedException.class)
    public void shouldThrowAuthenticationExceptionIfInvalidCredentials() throws Exception {
        TflGateway tflGateway = new TflGateway(wireMockTarget, INVALID_AUTH, INVALID_AUTH);
        tflGateway.getStatus("tube");
    }
}
