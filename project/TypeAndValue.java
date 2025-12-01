package project;

import Nodes.TypeEnum;

// represents a value of any type
// can do operations based on the type
public class TypeAndValue {
	public final TypeEnum type;
	public int intValue;
	public double doubleValue;
	public String stringValue;
	public boolean booleanValue;

	public TypeAndValue(TypeEnum type) {
		this.type = type;
	}
	public TypeAndValue(int intValue) {
		this.type = TypeEnum.Integer;
		this.intValue = intValue;
	}
	public TypeAndValue(String stringValue) {
		this.type = TypeEnum.String;
		this.stringValue = stringValue;
	}
	public TypeAndValue(double doubleValue) {
		this.type = TypeEnum.Double;
		this.doubleValue = doubleValue;
	}
	public TypeAndValue(boolean booleanValue) {
		this.type = TypeEnum.Boolean;
		this.booleanValue = booleanValue;
	}

	public TypeAndValue plus(TypeAndValue other) {
		if(this.type == TypeEnum.Integer && other.type == TypeEnum.Integer)
			return new TypeAndValue(this.intValue + other.intValue);
		if(this.type == TypeEnum.Double && other.type == TypeEnum.Double)
			return new TypeAndValue(this.doubleValue + other.doubleValue);
		return null;
	}

	public TypeAndValue minus(TypeAndValue other) {
		if(this.type == TypeEnum.Integer && other.type == TypeEnum.Integer)
			return new TypeAndValue(this.intValue - other.intValue);
		if(this.type == TypeEnum.Double && other.type == TypeEnum.Double)
			return new TypeAndValue(this.doubleValue - other.doubleValue);
		return null;
	}

	public TypeAndValue times(TypeAndValue other) {
		if(this.type == TypeEnum.Integer && other.type == TypeEnum.Integer)
			return new TypeAndValue(this.intValue * other.intValue);
		if(this.type == TypeEnum.Double && other.type == TypeEnum.Double)
			return new TypeAndValue(this.doubleValue * other.doubleValue);
		return null;
	}

	public TypeAndValue dividedBy(TypeAndValue other) {
		if(this.type == TypeEnum.Integer && other.type == TypeEnum.Integer)
			return new TypeAndValue(this.intValue / other.intValue);
		if(this.type == TypeEnum.Double && other.type == TypeEnum.Double)
			return new TypeAndValue(this.doubleValue / other.doubleValue);
		return null;
	}

	public TypeAndValue lessThan(TypeAndValue other) {
		if(this.type == TypeEnum.Integer && other.type == TypeEnum.Integer)
			return new TypeAndValue(this.intValue < other.intValue);
		if(this.type == TypeEnum.Double && other.type == TypeEnum.Double)
			return new TypeAndValue(this.doubleValue < other.doubleValue);
		return null;
	}

	public TypeAndValue lessThanOrEqual(TypeAndValue other) {
		if(this.type == TypeEnum.Integer && other.type == TypeEnum.Integer)
			return new TypeAndValue(this.intValue <= other.intValue);
		if(this.type == TypeEnum.Double && other.type == TypeEnum.Double)
			return new TypeAndValue(this.doubleValue <= other.doubleValue);
		return null;
	}

	public TypeAndValue greaterThan(TypeAndValue other) {
		return other.lessThanOrEqual(this);
	}

	public TypeAndValue greaterThanOrEqual(TypeAndValue other) {
		return other.lessThan(this);
	}

	public TypeAndValue equals(TypeAndValue other) {
		if(this.type == TypeEnum.Integer && other.type == TypeEnum.Integer)
			return new TypeAndValue(this.intValue == other.intValue);
		if(this.type == TypeEnum.Double && other.type == TypeEnum.Double)
			return new TypeAndValue(this.doubleValue == other.doubleValue);
		if(this.type == TypeEnum.String && other.type == TypeEnum.String)
			return new TypeAndValue(this.stringValue.equals(other.stringValue));
		return new TypeAndValue(this.booleanValue == other.booleanValue);
	}

	public TypeAndValue notEquals(TypeAndValue other) {
		TypeAndValue result = this.equals(other);
		result.booleanValue = !result.booleanValue;
		return result;
	}


}
