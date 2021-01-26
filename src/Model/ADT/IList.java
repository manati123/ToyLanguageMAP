package Model.ADT;

import java.util.Collection;

public interface IList<T> {
    void add(T newItem);
    void empty();

    Collection<? extends String> getElementsStrings();
}
