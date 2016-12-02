package be.techniquez.homeautomation.flukso;

import java.time.Instant;

/**
 * A datapoint.
 * 
 * @author alex
 */
public final class DataPoint {

	/** The timestamp. */
	private final Instant timestamp;
	
	/** The value. */
	private final long value;
	
	/**
	 * Create a new instance.
	 * 
	 * @param 	unixTimestamp	UNIX timestamp.
	 * @param 	value			The value.
	 */
	DataPoint(final long unixTimestamp, final long value) {
		this.timestamp = Instant.ofEpochSecond(unixTimestamp);
		this.value = value;
	}

	/**
	 * Returns the timestamp.
	 * 
	 * @return	The timestamp.
	 */
	public final Instant getTimestamp() {
		return timestamp;
	}

	/**
	 * Returns the value.
	 * 
	 * @return	The value.
	 */
	public final long getValue() {
		return value;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public final String toString() {
		final StringBuilder builder = new StringBuilder("Data point : timestamp [").append(this.timestamp).append("], ");
		builder.append("value : [").append(this.value).append("]");
		
		return builder.toString();
	}
}
