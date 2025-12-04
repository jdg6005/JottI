package project;

import Nodes.TypeEnum;
import provided.Token;

// represents a value of any type
// can do operations based on the type
// author: Miles Todtfeld
public class TypeAndValue {
	public final TypeEnum type;
    public boolean initialized = false;
	private int intValue;
	private double doubleValue;
	private String stringValue;
	private boolean booleanValue;

	public TypeAndValue(TypeEnum type) {
		this.type = type;
	}
	public TypeAndValue(int intValue) {
		this.type = TypeEnum.Integer;
		this.intValue = intValue;
        this.initialized = true;
	}
	public TypeAndValue(String stringValue) {
		this.type = TypeEnum.String;
		this.stringValue = stringValue;
        this.initialized = true;
    }
	public TypeAndValue(double doubleValue) {
		this.type = TypeEnum.Double;
		this.doubleValue = doubleValue;
        this.initialized = true;
    }
	public TypeAndValue(boolean booleanValue) {
		this.type = TypeEnum.Boolean;
		this.booleanValue = booleanValue;
        this.initialized = true;
    }

    public int getIntValue() {
        if(!initialized) { throw new UninitializedValueException(); }
        return intValue;
    }

    public double getDoubleValue() {
        if(!initialized) { throw new UninitializedValueException(); }
        return doubleValue;
    }

    public String getStringValue() {
        if(!initialized) { throw new UninitializedValueException(); }
        return stringValue;
    }

    public boolean getBooleanValue() {
        if(!initialized) { throw new UninitializedValueException(); }
        return booleanValue;
    }

	// sets the value of this to the value of other
	public void set(TypeAndValue other) {
        this.initialized = true;
		switch(other.type) {
			case TypeEnum.Integer -> this.intValue = other.intValue;
			case TypeEnum.Double -> this.doubleValue = other.doubleValue;
			case TypeEnum.String -> this.stringValue = other.stringValue;
			case TypeEnum.Boolean -> this.booleanValue = other.booleanValue;
		}
	}

	public TypeAndValue plus(TypeAndValue other) {
		if(this.type == TypeEnum.Integer && other.type == TypeEnum.Integer)
			return new TypeAndValue(this.getIntValue() + other.getIntValue());
		if(this.type == TypeEnum.Double && other.type == TypeEnum.Double)
			return new TypeAndValue(this.getDoubleValue() + other.getDoubleValue());
		return null;
	}

	public TypeAndValue minus(TypeAndValue other) {
		if(this.type == TypeEnum.Integer && other.type == TypeEnum.Integer)
			return new TypeAndValue(this.getIntValue() - other.getIntValue());
		if(this.type == TypeEnum.Double && other.type == TypeEnum.Double)
			return new TypeAndValue(this.getDoubleValue() - other.getDoubleValue());
		return null;
	}

	public TypeAndValue times(TypeAndValue other) {
		if(this.type == TypeEnum.Integer && other.type == TypeEnum.Integer)
			return new TypeAndValue(this.getIntValue() * other.getIntValue());
		if(this.type == TypeEnum.Double && other.type == TypeEnum.Double)
			return new TypeAndValue(this.getIntValue() * other.getIntValue());
		return null;
	}

	public TypeAndValue dividedBy(TypeAndValue other, Token divSymbol) {
		if(this.type == TypeEnum.Integer && other.type == TypeEnum.Integer) {
            if (other.getIntValue() == 0) {
                throw new Error("Runtime Error:\nDivision by 0!\n" + divSymbol.getFilename() + ":" + divSymbol.getLineNum());
            }
            return new TypeAndValue(this.getIntValue() / other.getIntValue());
        }
		if(this.type == TypeEnum.Double && other.type == TypeEnum.Double) {
            if(other.getDoubleValue() == 0.0) {
                throw new Error("Runtime Error:\nDivision by 0!\n" + divSymbol.getFilename() + ":" + divSymbol.getLineNum());
            }
            return new TypeAndValue(this.getDoubleValue() / other.getDoubleValue());
        }
		return null;
	}

	public TypeAndValue lessThan(TypeAndValue other) {
		if(this.type == TypeEnum.Integer && other.type == TypeEnum.Integer)
			return new TypeAndValue(this.getIntValue() < other.getIntValue());
		if(this.type == TypeEnum.Double && other.type == TypeEnum.Double)
			return new TypeAndValue(this.getDoubleValue() < other.getDoubleValue());
		return null;
	}

	public TypeAndValue lessThanOrEqual(TypeAndValue other) {
		if(this.type == TypeEnum.Integer && other.type == TypeEnum.Integer)
			return new TypeAndValue(this.getIntValue() <= other.getIntValue());
		if(this.type == TypeEnum.Double && other.type == TypeEnum.Double)
			return new TypeAndValue(this.getDoubleValue() <= other.getDoubleValue());
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
			return new TypeAndValue(this.getIntValue() == other.getIntValue());
		if(this.type == TypeEnum.Double && other.type == TypeEnum.Double)
			return new TypeAndValue(this.getDoubleValue() == other.getDoubleValue());
		if(this.type == TypeEnum.String && other.type == TypeEnum.String)
			return new TypeAndValue(this.getStringValue().equals(other.getStringValue()));
		return new TypeAndValue(this.getBooleanValue() == other.getBooleanValue());
	}

	public TypeAndValue notEquals(TypeAndValue other) {
		TypeAndValue result = this.equals(other);
		result.booleanValue = !result.booleanValue;
		return result;
	}

    public String toString() {
        return switch (this.type) {
            case Integer -> Integer.toString(this.intValue);
            case Double -> Double.toString(this.doubleValue);
            case String -> this.stringValue;
            case Boolean -> Boolean.toString(this.booleanValue);
            case Void -> null;
        };
    }

}
