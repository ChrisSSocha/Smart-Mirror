package technology.socha.chris.smartmirror.travel.gateways;

import org.apache.commons.lang3.StringUtils;
import technology.socha.chris.smartmirror.travel.models.TubeLine;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import java.util.List;

public class TflGateway {

    private final WebTarget endpoint;
    private final String appId;
    private final String appKey;

    public TflGateway(WebTarget endpoint, String appId, String appKey) {
        this.endpoint = endpoint;
        this.appId = appId;
        this.appKey = appKey;
    }

    public List<TubeLine> getStatus(String... modes){

        String modesCsv = StringUtils.join(modes, ',');

        return endpoint.path(modesCsv)
                .path("Status")
                .queryParam("detail", "False")
                .queryParam("app_id", appId)
                .queryParam("app_key", appKey)
                .request().get(new GenericType<List<TubeLine>>() {
                });
    }
}
