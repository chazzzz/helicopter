package org.kumosutra.helicopter.model;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

import java.util.Date;

/**
 * Created by chazz on 9/22/16.
 */
@Entity(value="breakouts", noClassnameStored = true)
@Indexes(
    @Index(fields = {@Field("date"), @Field("type")})
)
public class Breakout extends Document {

	private String symbol;

	private Date date;

	private Type type;

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public enum Type {
		TENKAN_SEN,
		KIJUN_SEN
	}
}
