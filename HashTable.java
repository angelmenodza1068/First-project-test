
// HashTable.java - code for hashing assignment
// Author: ?????
// Date:   ?????
// Class:  CS165
// Email:  ?????

import java.util.*;

public class HashTable implements IHash {
	// Method of hashing
	private HashFunction hasher;
	// Hash table : an ArrayList of "buckets", which store the actual strings
	private ArrayList<List<String>> hashTable;
	/**
	 * Number of Elements
	 */
	private int numberOfElements;
	private int numberOfBuckets;

	/**
	 * Initializes a new instance of HashTable.
	 * <p>
	 * Initialize the instance variables. <br>
	 * Note: when initializing the hashTable, make sure to allocate each entry in the HashTable
	 *       to a new a HashBucket or null, your choice.
	 * @param numberOfBuckets   the size of the hashTable
	 * @param hasher the type of hashing function
	 */
	public HashTable(int numberOfBuckets, HashFunction hasher) {
		this.numberOfBuckets = numberOfBuckets;
		this.numberOfElements = 0;
		this.hasher = hasher;
		this.hashTable = new ArrayList<List<String>>(numberOfBuckets);
		for(int i =0;i<numberOfBuckets;i++) {
			hashTable.add(new LinkedList<String>());
		}
	}

	public boolean insert(String key) {
		int index = hasher.hash(key) % numberOfBuckets;
		if(hashTable.get(index).contains(key)) {
			return false;
		}
		else {
			hashTable.get(index).add(key);
			numberOfElements++;
			return true;
		}


	}

	public boolean remove(String key) {
		int index = hasher.hash(key) % numberOfBuckets;
		if(hashTable.get(index).contains(key)) {
			numberOfElements--;
			return true;
		}
		return false;
	}

	public String search(String key) {
		int index = hasher.hash(key) % numberOfBuckets;
		if(hashTable.get(index).contains(key)) {
			return key;
		}
		return null;
	}


	public int size() {
		return numberOfElements;

	}

	public int size(int index) {
		return hashTable.get(index).size();

	}

	// Return iterator for the entire hashTable,
	// must iterate all hashBuckets that are not empty
	public class HashIterator implements Iterator<String> {
		// The current index into the hashTable
		private int currentIndex;
		// The current iterator for that hashBucket
		private Iterator<String> currentIterator = null;

		HashIterator() {
            for(int i =0;i<numberOfBuckets;i++) {
                if(!hashTable.get(i).isEmpty()) {
                    currentIndex = i;
                    break;
                }      
            }
            currentIterator = hashTable.get(currentIndex).iterator();
        }

        public String next() {
            if(hasNext()) {
                return currentIterator.next();
            }
            else {
                throw new IllegalStateException();
//                if(!currentIterator.hasNext()) {
//                    for(int i =currentIndex;i<numberOfBuckets;i++) {
//                        if(!hashTable.get(i).isEmpty()) {
//                        currentIndex = i;
//                        currentIterator = hashTable.get(currentIndex).iterator();
//                         return currentIterator.next();
//                    }
//                     
//                }
//            
//            }
//        }
//            return null;
        }
        }
        public boolean hasNext() {
            if(currentIterator.hasNext()) {
                return true;
            }
            else{
       
                 for(int i =currentIndex+1;i<numberOfBuckets;i++) {
                        if(!hashTable.get(i).isEmpty()) {
                        currentIndex = i;
                        currentIterator = hashTable.get(currentIndex).iterator();
                          return true;
                    }

                }
                
                
                
            }
        
           return false;
        }
    }

    // Return an iterator for the hash table
    public Iterator<String> iterator() {
           return new HashIterator();

        
        // YOUR CODE HERE
       
    }
	/**
	 * Does not use the iterator above. Iterates over one bucket.
	 *
	 * @param index the index of bucket to iterate over
	 * @return an iterator for that bucket
	 */
	public Iterator<String> iterator(int index) {
		List<String> bucket = hashTable.get(index);
		return bucket != null ? bucket.iterator() : null;
	}

	// Prints entire hash table.
	// NOTE: This method is used extensively for testing.
	public void print() {
		Debug.printf("HashTable size: " + size());

		for (int index = 0; index < hashTable.size(); index++) {
			List<String> list = hashTable.get(index);
			if (list != null) {
				Debug.printf("HashTable[%d]: %d entries", index, list.size());
				for (String word : list) {
					Debug.printf("String: %s (hash code %d)", word, hasher.hash(word));
				}
			}else {
				Debug.printf("HashTable[%d]: %d entries", index, 0);
			}
		}
	}
}