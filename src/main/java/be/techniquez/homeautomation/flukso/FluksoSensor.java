package be.techniquez.homeautomation.flukso;

import java.io.IOException;
import java.util.List;

/**
 * Flukso sensor.
 * 
 * @author alex
 *
 */
public interface FluksoSensor {
	
	/** Enumerates the types. */
	enum Type {
		ELECTRICITY(Unit.WATT),
		GAS(Unit.LITER_PER_DAY);
		
		/** Measurement unit. */
		private Unit unit;
		
		/**
		 * Create a new instance.
		 * 
		 * @param 	unit	The {@link Unit}.
		 */
		private Type(final Unit unit) {
			this.unit = unit;
		}
		
		/**
		 * Returns the {@link Unit}.
		 * 
		 * @return	The {@link Unit}.
		 */
		public final Unit getUnit() {
			return this.unit;
		}
	}

	/**
	 * Returns the readouts.
	 * 
	 * @return	The latest readouts.
	 * 
	 * @throws 	IOException		If an IO error occurs.
	 */
	List<DataPoint> getDataPoints() throws IOException;
	
	/**
	 * Returns the ID.
	 * 
	 * @return	The ID.
	 */
	String getId();
	
	/**
	 * Returns the {@link Unit}.
	 * 
	 * @return	The {@link Unit}.
	 */
	Type getType();
}
