import java.io.Reader;
import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
   private Resume[] storage = new Resume[10000];
    private int size = 0;
    void clear() {
        storage = new Resume[10000];
        size = 0;
    }

    void save(Resume r) {
        if (Objects.nonNull(r)) storage[size++] = r;
    }

    public Resume get(String uuid) {
        for (int i = 0; i < size; i++)
            if (uuid.equals(storage[i].getUuid())) return storage[i];
        return null;
    }

   public void delete(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                for (int j = i; j < size; j++)
                    storage[j] = storage[j + 1];
                break;
            }
        }
        size--;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
   public Resume[] getAll() {
       Resume[] resumes = new Resume[size];
       System.arraycopy(storage, 0, resumes, 0, size);
        return resumes;
    }

    public int size() {
        return size;
    }
}
