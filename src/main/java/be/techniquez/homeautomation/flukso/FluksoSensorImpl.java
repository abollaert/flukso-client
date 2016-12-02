package be.techniquez.homeautomation.flukso;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.protocol.HttpProcessor;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

public final class FluksoSensorImpl implements FluksoSensor {
	
	/** The parameter that indicates the version. */
	private static final String PARAM_API_VERSION = "version";
	
	/** The interval parameter. */
	private static final String PARAM_INTERVAL = "interval";
	
	/** The API version. */
	private static final String API_VERSION = "1.0";
	
	/** The token header. */ 
	private static final String INTERVAL = "minute"; 
	
	/** Unit parameter. */
	private static final String PARAM_UNIT = "unit";
	
	/** The port. */
	private static int PORT = 8080;
	
	/** The ID. */
	private final String id;
	
	/** The {@link HttpProcessor}. */
	private final HttpClient httpClient;
	
	/** The {@link HttpHost}. */
	private final HttpHost host;
	
	/** The {@link Type}. */
	private final Type type;
	
	/**
	 * Create a new instance.
	 * 
	 * @param 	httpClient		The {@link HttpClient}.
	 * @param	host			The host.
	 * @param 	id				The ID.
	 * @param	type			The {@link Type}.
	 */
	FluksoSensorImpl(final HttpClient httpClient, final String host, final String id, final Type type) {
		this.httpClient = httpClient;
		this.id = id;
		this.type = type;
		this.host = new HttpHost(host, PORT);
	}
	
	/**
	 * Create the {@link HttpRequest} to get the data.
	 * 
	 * @return	The {@link HttpRequest} to get the data.
	 */
	private final HttpRequest createRequest() {
		final StringBuilder uriBuilder = new StringBuilder("/sensor/");
		uriBuilder.append(this.id).append("?");
		uriBuilder.append(PARAM_API_VERSION).append("=").append(API_VERSION).append("&");
		uriBuilder.append(PARAM_INTERVAL).append("=").append(INTERVAL).append("&");
		uriBuilder.append(PARAM_UNIT).append("=").append(this.type.getUnit().getName());

		return new HttpGet(uriBuilder.toString());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final List<DataPoint> getDataPoints() throws IOException {
		final HttpResponse response = this.httpClient.execute(this.host, this.createRequest());
		
		if (response != null) {
			if (response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK) {
				try (final InputStreamReader reader = new InputStreamReader(response.getEntity().getContent())) {
					final Object parsed = JSONValue.parse(reader);
					
					if (parsed instanceof JSONArray) {
						final List<DataPoint> dataPoints = new ArrayList<>();
						
						final JSONArray jsonValues = (JSONArray)parsed;
						
						for (final Object jsonPoint : jsonValues) {
							if (jsonPoint instanceof JSONArray) {
								final JSONArray pointValues = (JSONArray)jsonPoint;
								
								final long timestamp = (Long)pointValues.get(0);
								final Object value = pointValues.get(1);
								
								if (value instanceof Long) {
									dataPoints.add(new DataPoint(timestamp, (Long)value));
								}
							}
						}
						
						return dataPoints;
					} else {
						throw new IOException("Error reading data from flukso : returned object is not a JSON array : [" + parsed + "]");
					}
				}
			} else {
				throw new IOException("Error reading data from flukso : status code [" + response.getStatusLine().getStatusCode() + "]");
			}
		} else {
			throw new IOException("Error reading data from flukso : response was null !");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String getId() {
		return this.id;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final Type getType() {
		return this.type;
	}
}
