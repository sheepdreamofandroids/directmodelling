package com.directmodelling.api;

import java.io.Serializable;

/**
 * Implementors are entities, not values. They have a ID which is valid over
 * both client and server. At the moment only the single server can create new
 * IDs. ID's are unique within the lifetime of the application.
 * 
 * @author guus
 *
 */
public interface Identifiable extends Serializable {
	/** An ID that is valid on all connected systems. */
	ID id();
}
