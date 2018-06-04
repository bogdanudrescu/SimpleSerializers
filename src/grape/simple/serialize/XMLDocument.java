package grape.simple.serialize;

import java.util.LinkedList;
import java.util.List;

/**
 * XML text document. Use it to easily generate XML content as a String.
 *
 * @author Bogdan Udrescu (bogdan.udrescu@gmail.com)
 */
public class XMLDocument {

    /*
     * The xml string.
     */
    private StringBuilder xml;

    /*
     * The text indentation.
     */
    private int indent = 0;

    /*
     * The elements started
     */
    private LinkedList<String> startedElements = new LinkedList<String>();

    /**
     * Create an xml text document.
     */
    public XMLDocument() {
        xml = new StringBuilder();
    }

    /*
     * The indent space.
     */
    private String indentSpace = " ";

    /**
     * Sets the number of spaces for the indentation.
     * 
     * @param spaces
     *            the number of spaces for the indentation.
     */
    public void setIndent(int spaces) {

        if (xml.length() > 0) {
            // We can't allow the indent set after the user already pushed data
            // to the xml.

        } else {

            switch (spaces) {
                case 0:
                    indentSpace = "";
                    break;

                case 1:
                    indentSpace = " ";
                    break;

                case 2:
                    indentSpace = "  ";
                    break;

                case 4:
                    indentSpace = "    ";
                    break;

                default:
                    char[] builder = new char[spaces];
                    for (int i = 0; i < spaces; i++) {
                        builder[i] = ' ';
                    }
                    indentSpace = new String(builder);
                    break;
            }

        }

    }

    /*
     * Indent the xml
     */
    private void addIndent() {
        if (indentSpace.length() > 0) {
            for (int i = 0; i < indent; i++) {
                xml.append(indentSpace);
            }
        }
    }

    /**
     * Append a start element.
     * 
     * @param name
     *            the name of the element.
     * @return the xml document.
     */
    public XMLDocument startElement(String name) {
        return this.startElement(name, null, false);
    }

    /*
     * Append a start element.
     */
    private XMLDocument startElement(String name, boolean closeTag) {
        return this.startElement(name, null, closeTag);
    }

    /**
     * Append a start element.
     * 
     * @param name
     *            the name of the element.
     * @param attributes
     *            the attributes.
     * @return the xml document.
     */
    public XMLDocument startElement(String name, Object[] attributes) {
        return this.startElement(name, attributes, false);
    }

    /*
     * Append a start element.
     */
    private XMLDocument startElement(String name, Object[] attributes, boolean closeTag) {
        return this.startElement(name, attributes, closeTag, true);
    }

    /*
     * Append a start element.
     */
    private XMLDocument startElement(String name, Object[] attributes, boolean closeTag, boolean addNewLine) {
        this.addIndent();

        indent++;

        xml.append("<").append(name);

        this.addAttributes(attributes);

        if (closeTag) {
            xml.append("/");
        }

        xml.append(">");

        if (closeTag || addNewLine) {
            xml.append("\n");
        }

        if (closeTag) {
            indent--;

        } else {
            startedElements.addLast(name);
        }

        return this;
    }

    /**
     * Append the end element.
     * 
     * @return the xml document.
     */
    public XMLDocument endElement() {
        return this.endElement(null);
    }

    /**
     * Append the end element.
     * 
     * @param name
     *            the name of the element to close.
     * @return the xml document.
     * @deprecated use {@link #endElement()} instead.
     */
    public XMLDocument endElement(String name) {
        return this.endElement(name, true);
    }

    /*
     * Append the end element.
     */
    private XMLDocument endElement(String name, boolean addIndent) {
        String endName = startedElements.removeLast();

        if (name != null) {
            if (!endName.equals(name)) {
                throw new IllegalArgumentException("Close the element " + endName + " instead of " + name);
            }

        } else {
            name = endName;
        }

        indent--;

        if (addIndent) {
            this.addIndent();
        }

        xml.append("</").append(name).append(">");
        xml.append("\n");

        return this;
    }

    /**
     * Append an element.
     * 
     * @param name
     *            the name of the element.
     * @return the xml document.
     */
    public XMLDocument addElement(String name) {
        return this.addElement(name, null, null);
    }

    /**
     * Append an element.
     * 
     * @param name
     *            the name of the element.
     * @param content
     *            the content of the element.
     * @return the xml document.
     */
    public XMLDocument addElement(String name, Object content) {
        return this.addElement(name, null, content);
    }

    /**
     * Append an element.
     * 
     * @param name
     *            the name of the element.
     * @param attributes
     *            the attributes.
     * @param content
     *            the content of the element.
     * @return the xml document.
     */
    public XMLDocument addElement(String name, Object[] attributes) {
        return this.addElement(name, attributes, null);
    }

    /**
     * Append an element.
     * 
     * @param name
     *            the name of the element.
     * @param attributes
     *            the attributes.
     * @param content
     *            the content of the element.
     * @return the xml document.
     */
    public XMLDocument addElement(String name, Object[] attributes, Object content) {

        this.startElement(name, attributes, content == null, false);

        if (content != null) {
            xml.append(content);
            this.endElement(name, false);
        }

        return this;
    }

    /*
     * Append the attributes.
     */
    private XMLDocument addAttributes(Object[] attributes) {
        if (attributes != null) {
            for (int i = 0; i < attributes.length; i += 2) {
                Object name = attributes[i];

                if (name instanceof List<?>) {
                    List<?> attributesList = (List<?>) name;
                    i--;

                    addAttributes(attributesList);

                } else {
                    Object value = attributes[i + 1];
                    xml.append(" ").append(name).append("=\"").append(value).append("\"");
                }
            }
        }

        return this;
    }

    /*
     * Append the attributes.
     */
    private XMLDocument addAttributes(List<?> attributes) {
        if (attributes != null) {
            for (int i = 0; i < attributes.size(); i += 2) {
                Object name = attributes.get(i);

                if (name instanceof List<?>) {
                    List<?> attributesList = (List<?>) name;
                    i--;

                    addAttributes(attributesList);

                } else {
                    Object value = attributes.get(i + 1);
                    xml.append(" ").append(name).append("=\"").append(value).append("\"");
                }
            }
        }

        return this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        // if (startedElements.isEmpty())

        return xml.toString();
    }

    // public void writeToHttpResponse(HttpServletResponse response) {
    // response.setContentType("application/xml");
    // response.getOutputStream().write(xml.getBytes());
    //
    // }

}
