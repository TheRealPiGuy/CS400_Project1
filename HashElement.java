// --== CS400 File Header Information ==--
// Name: Michael Knoll
// Email: mjknoll2@wisc.edu
// Team: Red
// Group: FB
// TA: Daniel Finer
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

/**
 * This class allows key-value pairs to be grouped together so they
 * can be added as one object to the hashtable
 * @author Michael
 *
 * @param <KeyType> generic type of key field
 * @param <ValueType> generic type of value field
 */
public class HashElement <KeyType, ValueType> {
  private KeyType key;
  private ValueType value;
  
  /**
   * Constructor that sets key and value fields for this HashElement
   * @param key value for key field
   * @param value value for value field
   */
  public HashElement(KeyType key, ValueType value) {
    this.key = key;
    this.value = value;
  }
  
  /**
   * Returns the key field of this HashElement
   * @return this.key
   */
  public KeyType getKey() {
    return this.key;
  }
  
  /**
   * Returns the value field of this HashElement
   * @return
   */
  public ValueType getValue() {
    return this.value;
  }

}
