package be.techniquez.homeautomation.flukso;

/**
 * Enumerates the available units.
 * 
 * @author alex
 *
 */
public enum Unit {

	WATT("watt"),
	LITER_PER_DAY("lperday");
	
	/** The name as encoded in the request. */
	private final String name;
	
	/** 
	 * Create a new instance.
	 * 
	 * @param 	name		Name as encoded in the request.
	 */
	private Unit(final String name) {
		this.name = name;
	}
	
	/**
	 * Returns the name.
	 * 
	 * @return	The name.
	 */
	public final String getName() {
		return name;
	}
}
