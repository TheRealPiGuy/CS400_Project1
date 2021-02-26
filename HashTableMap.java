// --== CS400 File Header Information ==--
// Name: Michael Knoll
// Email: mjknoll2@wisc.edu
// Team: Red
// Group: FB
// TA: Daniel Finer
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>
import java.util.NoSuchElementException;
import java.util.LinkedList;

/**
 * This class implements methods from the MapADT interface to create a 
 * hashtable. It also provides methods to add and remove elements and handles
 * exceptions that might occur during use.
 * @author Michael
 *
 * @param <KeyType> generic type for the key field of each element
 * @param <ValueType> generic type for the value field of each element
 */
public class HashTableMap <KeyType, ValueType>implements MapADT<KeyType, ValueType> {
  private int size = 0;
  @SuppressWarnings("rawtypes")
  private LinkedList[] hashTable;
  
  /**
   * Constructor used to initialize a hashtable with a length of capacity
   * @param capacity length of hashtable
   */
  public HashTableMap(int capacity) {
    hashTable = new LinkedList[capacity];
  }
  
  /**
   * Default constructor used to initialize a hashtable with a length of 10
   */
  public HashTableMap() {
    hashTable = new LinkedList[10];
  }

  /**
   * This method adds a new key-value pair to the hashtable and can expand the array
   * if the load capacity exceeds or is equal to 0.85
   * @param key value of key used for determining hashcode
   * @param value value stored in hashtable
   */
  @SuppressWarnings("unchecked")
  @Override
  public boolean put(KeyType key, ValueType value) {
    int index;
    
    // check if key is null
    if (key == null) {
      return false;
    }
    
    // if key isn't null create index and HashElement
    index = Math.abs(key.hashCode()) % hashTable.length;
    HashElement<KeyType, ValueType> element = new HashElement<KeyType, ValueType>(key, value);
    
    // check if there is a LinkedList at the index, if not, create one and add HashElelement
    if (hashTable[index] == null) {
      LinkedList<HashElement<KeyType, ValueType>> list = new LinkedList<HashElement<KeyType, ValueType>>();
      hashTable[index] = list;
      hashTable[index].add(element);
      this.size = this.size + 1;
      if ((double) this.size / (double) hashTable.length >= 0.85) {
        rehashHelper();
      }
      return true;
    }
    
    // search if key is in table already, if so, don't add and return false
    for (int i = 0; i < hashTable[index].size(); ++i) {
      if (((HashElement<KeyType, ValueType>) hashTable[index].get(i)).getKey().equals(key)) {
        return false;
      }
    }
    
    // if LinkedList is created already and key isn't in table then add HashElement and return true
    hashTable[index].add(element);
    this.size = this.size + 1;
    if ((double) this.size / (double) hashTable.length >= 0.85) {
      rehashHelper();
    }
    return true;
    
  }
  
  /**
   * This private helper method doubles the capacity of the hashtable and rehashes
   * the elements when the load capacity reaches 0.85 or greater
   */
  @SuppressWarnings("unchecked")
  private void rehashHelper() {
    @SuppressWarnings("rawtypes")
    
    // iterate through old hashtable and copy values to new one
    LinkedList[] rehashed = new LinkedList[hashTable.length * 2]; 
    for (int i = 0; i < hashTable.length; ++i) {
      if (hashTable[i] == null) {
        break;
      }
      for (int j = 0; j < hashTable[i].size(); ++j) {
     // if key isn't null create index and HashElement
        int index = Math.abs(((HashElement<KeyType, ValueType>) hashTable[i].get(j)).getKey().hashCode()) % rehashed.length;
        HashElement<KeyType, ValueType> element = (HashElement<KeyType, ValueType>) hashTable[i].get(j);
        
        // check if there is a LinkedList at the index, if not, create one and add HashElelement
        if (rehashed[index] == null) {
          LinkedList<HashElement<KeyType, ValueType>> list = new LinkedList<HashElement<KeyType, ValueType>>();
          rehashed[index] = list;
          rehashed[index].add(element);
          break;
          }
        
        rehashed[index].add(element);
        }
      }
    hashTable = rehashed;
    }

  /**
   * This method locates and returns a key-value pair in the hashtable 
   * without removing it.
   * @param key key in key-value pair to search for
   * @throws NoSuchElementException if value isn't found
   */
  @SuppressWarnings("unchecked")
  @Override
  public ValueType get(KeyType key) throws NoSuchElementException {
    
    // if key is null return null
    if (key == null) {
      return null;
    }
    int index = Math.abs(key.hashCode()) % hashTable.length;
    
    // iterate through hashTable to find any matches, if so, return the value
    if (hashTable[index] == null) {
      throw new NoSuchElementException();
    } else {
      for (int i = 0; i < hashTable[index].size(); ++i) {
        if (((HashElement<KeyType, ValueType>) hashTable[index].get(i)).getKey().equals(key)) {
          return ((HashElement<KeyType, ValueType>) hashTable[index].get(i)).getValue();
        }
      }
    }
    throw new NoSuchElementException();
  }

  /**
   * Returns the number of elements in the hashtable
   */
  @Override
  public int size() {
    return this.size;
  }

  /**
   * Returns true if the key parameter is in the hashtable and false if not
   * @param key the key in key-value pair to search for
   */
  @SuppressWarnings("unchecked")
  @Override
  public boolean containsKey(KeyType key) {
    int index = Math.abs(key.hashCode()) % hashTable.length;
    
    // iterate through LinkedList at index to check for matching key
    if (hashTable[index] == null) {
      return false;
    }
    for (int i = 0; i < hashTable[index].size(); ++i) {
      if (((HashElement<KeyType, ValueType>) hashTable[index].get(i)).getKey().equals(key)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Removes the specified key-value pair from the list and returns it. Returns null if value
   * isn't in hashtable
   * @param key the key in key-value pair desired to be removed
   */
  @SuppressWarnings("unchecked")
  @Override
  public ValueType remove(KeyType key) {
    int index = Math.abs(key.hashCode()) % hashTable.length;
    
    // iterate through LinkedList at index to check for matching key
    if (hashTable[index] == null) {
      return null;
    }
    for (int i = 0; i < hashTable[index].size(); ++i) {
      if (((HashElement<KeyType, ValueType>) hashTable[index].get(i)).getKey().equals(key)) {
        this.size = this.size - 1;
        ValueType temp = ((HashElement<KeyType, ValueType>) hashTable[index].get(i)).getValue();
        hashTable[index].remove(i);
        return temp;
      }
    }
    
    return null;
  }

  /**
   * Clears the hashtable of all references
   */
  @Override
  public void clear() {
    // sets all references in table to null
    for (int i = 0; i < hashTable.length; ++i) {
      hashTable[i] = null;
    }
    this.size = 0;
    
  }
  
  /**
   * Returns the length of the array used for the hashTable
   * @return length of array used for hashTable
   */
  public int length() {
    return hashTable.length;
  }

}
