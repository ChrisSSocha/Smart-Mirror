package unit.technology.chris.smartmirror.travel.resources;

import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Rule;
import org.junit.Test;
import technology.socha.chris.smartmirror.travel.gateways.TflGateway;
import technology.socha.chris.smartmirror.travel.models.TubeLine;
import technology.socha.chris.smartmirror.travel.resources.TravelResource;

import javax.ws.rs.core.GenericType;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TravelResourceTest {

    public TflGateway tflGateway = mock(TflGateway.class);

    @Rule
    public final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new TravelResource(tflGateway))
            .build();

    @Test
    public void shouldReturnListOfTubeStatues(){

        List<TubeLine> expected = newArrayList(new TubeLine("line1", "Line 1", null), new TubeLine("line2", "Line 2", null));
        when(tflGateway.getStatus("tube,dlr")).thenReturn(expected);

        List<TubeLine> actual = resources.client().target("/travel/tube").request().get(new GenericType<List<TubeLine>>() {});

        assertThat(actual).isEqualTo(expected);
    }

}
