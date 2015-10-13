package technology.socha.chris.smartmirror.admin.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("version")
@Produces(MediaType.TEXT_PLAIN)
public class VersionResource {

    @GET
    public String getVersion(){
        return VersionResource.class.getPackage().getImplementationVersion();
    }
}
