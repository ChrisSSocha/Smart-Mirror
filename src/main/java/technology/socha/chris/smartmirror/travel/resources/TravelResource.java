package technology.socha.chris.smartmirror.travel.resources;

import technology.socha.chris.smartmirror.travel.models.TubeLine;
import technology.socha.chris.smartmirror.travel.gateways.TflGateway;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/travel")
@Produces(MediaType.APPLICATION_JSON)
public class TravelResource {

    private final TflGateway statusService;

    public TravelResource(TflGateway statusService){
        this.statusService = statusService;
    }

    @GET
    @Path("/tube")
    public List<TubeLine> getEvents() {
        return statusService.getStatus("tube");
    }

}
