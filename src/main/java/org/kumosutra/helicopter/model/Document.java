package org.kumosutra.helicopter.model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;

/**
 * Created by chazz on 9/24/16.
 */
public class Document {

	@Id
	private ObjectId objectId;

	public ObjectId getObjectId() {
		return objectId;
	}

	public void setObjectId(ObjectId objectId) {
		this.objectId = objectId;
	}
}
