package grape.simple.serialize;

/**
 * JSON text document implementation.
 * 
 * @author Bogdan Udrescu (bogdan.udrescu@gmail.com)
 */
public class JSONDocument {

	/*
	 * The buffer with the json.
	 */
	private StringBuilder json;

	/**
	 * Create a JSON text document.
	 */
	public JSONDocument() {
		json = new StringBuilder();
	}

	/**
	 * Adds a String entry into the json.
	 * @param name	the name of the entry.
	 * @param value	the value of the entry.
	 * @return	this instance of JSON.
	 */
	public JSONDocument addString(String name, String value) {
		if (value != null) {
			addComma();
			addKey(name);
			addValueAsString(value);
		}
		return this;
	}

	/**
	 * Adds a Number entry into the json.
	 * @param name	the name of the entry.
	 * @param value	the value of the entry.
	 * @return	this instance of JSON.
	 */
	public JSONDocument addNumber(String name, Number value) {
		if (value != null) {
			addComma();
			addKey(name);
			addValueAsObject(value);
		}
		return this;
	}

	/**
	 * Adds a boolean entry into the json.
	 * @param name	the name of the entry.
	 * @param value	the value of the entry.
	 * @return	this instance of JSON.
	 */
	public JSONDocument addBoolean(String name, boolean value) {
		addComma();
		addKey(name);
		json.append(value);

		return this;
	}

	/**
	 * Adds an int entry into the json.
	 * @param name	the name of the entry.
	 * @param value	the value of the entry.
	 * @return	this instance of JSON.
	 */
	public JSONDocument addInt(String name, int value) {
		addComma();
		addKey(name);
		json.append(value);

		return this;
	}

	/**
	 * Adds a float entry into the json.
	 * @param name	the name of the entry.
	 * @param value	the value of the entry.
	 * @return	this instance of JSON.
	 */
	public JSONDocument addFloat(String name, float value) {
		addComma();
		addKey(name);
		json.append(value);

		return this;
	}

	/**
	 * Adds a double entry into the json.
	 * @param name	the name of the entry.
	 * @param value	the value of the entry.
	 * @return	this instance of JSON.
	 */
	public JSONDocument addDouble(String name, double value) {
		addComma();
		addKey(name);
		json.append(value);

		return this;
	}

	/**
	 * Adds a float entry into the json.
	 * @param name	the name of the entry.
	 * @param value	the value of the entry.
	 * @return	this instance of JSON.
	 */
	public JSONDocument addLong(String name, long value) {
		addComma();
		addKey(name);
		json.append(value);

		return this;
	}

	/**
	 * Adds a list of strings to the JSON.
	 * @param name		the name of the entry.
	 * @param strings	the list of strings.
	 * @return	this instance of JSON.
	 */
	public JSONDocument addArray(String name, String[] strings) {
		if (strings != null && strings.length > 0) {
			addComma();
			addKey(name);

			json.append("[ ");
			for (int i = 0; i < strings.length; i++) {
				if (i > 0) {
					json.append(", ");
				}
				addValueAsString(strings[i]);
			}
			json.append(" ]");
		}
		return this;
	}

	/**
	 * Adds a list of numbers to the JSON.
	 * @param name		the name of the entry.
	 * @param numbers	the list of numbers.
	 * @return	this instance of JSON.
	 */
	public JSONDocument addArray(String name, Number[] numbers) {
		if (numbers != null && numbers.length > 0) {
			addComma();
			addKey(name);

			json.append("[ ");
			for (int i = 0; i < numbers.length; i++) {
				if (i > 0) {
					json.append(", ");
				}
				addValueAsObject(numbers[i]);
			}
			json.append(" ]");
		}
		return this;
	}

	/**
	 * Adds a JSON object into the json.
	 * @param name	the name of the entry.
	 * @param value	the value of the entry.
	 * @return	this instance of JSON.
	 */
	public JSONDocument addJSON(String name, JSONDocument value) {
		if (value != null) {
			addComma();
			addKey(name);
			addValueAsObject(value);
		}
		return this;
	}

	/*
	 * Add the comma if necessary.
	 */
	private void addComma() {
		if (json.length() > 0) {
			json.append(", ");
		}
	}

	/*
	 * Adds the key for a json entry.
	 */
	private void addKey(String name) {
		json.append("\"").append(name).append("\"").append(" : ");
	}

	/*
	 * Adds the value as an object.
	 */
	private void addValueAsObject(Object value) {
		json.append(value);
	}

	/*
	 * Append the value as a string wrapped with double quotes.
	 */
	private void addValueAsString(String value) {
		json.append("\"").append(jsonString(value)).append("\"");
	}

	/*
	 * String replacements
	 */
	private static final String[] TARGETS = new String[] { "\r", "\n", "\"" };
	private static final String[] REPLACEMENTS = new String[] { "\\r", "\\n", "\\\"" };

	/*
	 * Replace the bad chars from json strings.
	 */
	private String jsonString(String value) {
		return replace(value, TARGETS, REPLACEMENTS);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();

		buffer.append("{ ");
		buffer.append(json);
		buffer.append(" }");

		return buffer.toString();
	}

	/*
	 * TODO: this should stay in a StringUtils library. But it's much simple to have this API solely without any other needed references.
	 * We'll keep this private anyway, as long as it's here.
	 * 
	 * Replace all the text in targets with what's in replacements for the same position in array.
	 * @param text			the text to change.
	 * @param targets		what to replace.
	 * @param replacements	with what to replace.
	 * @return	the changed text.
	 */
	private static String replace(String text, String[] targets, String[] replacements) {
		for (int i = 0; i < targets.length; i++) {
			if (text.contains(targets[i])) {
				text = text.replace(targets[i], replacements[i]);
			}
		}

		return text;
	}

}
