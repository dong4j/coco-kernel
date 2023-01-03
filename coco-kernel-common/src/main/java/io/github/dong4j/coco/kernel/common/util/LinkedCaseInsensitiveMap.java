/*
 * Copyright 2002-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.dong4j.coco.kernel.common.util;

import org.jetbrains.annotations.Nullable;

import java.io.Serial;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * {@link LinkedHashMap} variant that stores String keys in a case-insensitive
 * manner, for example for key-based access in a results table.
 *
 * <p>Preserves the original order as well as the original casing of keys,
 * while allowing for contains, get and remove calls with any case of key.
 *
 * <p>Does <i>not</i> support {@code null} keys.
 *
 * @param <V> the value type
 * @author Juergen Hoeller
 * @version 1.0.0
 * @date 2023.01.03 09:58
 * @since 3.0
 */
public class LinkedCaseInsensitiveMap<V> implements Map<String, V>, Serializable, Cloneable {

    @Serial
    private static final long serialVersionUID = 2500492308859765730L;
    /** Target map */
    private final LinkedHashMap<String, V> targetMap;

    /** Case insensitive keys */
    private final HashMap<String, String> caseInsensitiveKeys;

    /** Locale */
    private final Locale locale;

    /** Key set */
    @Nullable
    private transient volatile Set<String> keySet;

    /** Values */
    @Nullable
    private transient volatile Collection<V> values;

    /** Entry set */
    @Nullable
    private transient volatile Set<Entry<String, V>> entrySet;


    /**
     * Create a new LinkedCaseInsensitiveMap that stores case-insensitive keys
     * according to the default Locale (by default in lower case).
     *
     * @see #convertKey(String)
     * @since 2023.1.1
     */
    public LinkedCaseInsensitiveMap() {
        this((Locale) null);
    }

    /**
     * Create a new LinkedCaseInsensitiveMap that stores case-insensitive keys
     * according to the given Locale (by default in lower case).
     *
     * @param locale the Locale to use for case-insensitive key conversion
     * @see #convertKey(String)
     * @since 2023.1.1
     */
    public LinkedCaseInsensitiveMap(@Nullable Locale locale) {
        this(16, locale);
    }

    /**
     * Create a new LinkedCaseInsensitiveMap that wraps a {@link LinkedHashMap}
     * with the given initial capacity and stores case-insensitive keys
     * according to the default Locale (by default in lower case).
     *
     * @param initialCapacity the initial capacity
     * @see #convertKey(String)
     * @since 2023.1.1
     */
    public LinkedCaseInsensitiveMap(int initialCapacity) {
        this(initialCapacity, null);
    }

    /**
     * Create a new LinkedCaseInsensitiveMap that wraps a {@link LinkedHashMap}
     * with the given initial capacity and stores case-insensitive keys
     * according to the given Locale (by default in lower case).
     *
     * @param initialCapacity the initial capacity
     * @param locale          the Locale to use for case-insensitive key conversion
     * @see #convertKey(String)
     * @since 2023.1.1
     */
    public LinkedCaseInsensitiveMap(int initialCapacity, @Nullable Locale locale) {
        this.targetMap = new LinkedHashMap<String, V>(initialCapacity) {
            @Override
            public boolean containsKey(Object key) {
                return LinkedCaseInsensitiveMap.this.containsKey(key);
            }

            @Override
            protected boolean removeEldestEntry(Map.Entry<String, V> eldest) {
                boolean doRemove = LinkedCaseInsensitiveMap.this.removeEldestEntry(eldest);
                if (doRemove) {
                    removeCaseInsensitiveKey(eldest.getKey());
                }
                return doRemove;
            }
        };
        this.caseInsensitiveKeys = new HashMap<>(initialCapacity);
        this.locale = locale != null ? locale : Locale.getDefault();
    }

    /**
     * Copy constructor.
     *
     * @param other other
     * @since 2023.1.1
     */
    @SuppressWarnings("unchecked")
    private LinkedCaseInsensitiveMap(LinkedCaseInsensitiveMap<V> other) {
        this.targetMap = (LinkedHashMap<String, V>) other.targetMap.clone();
        this.caseInsensitiveKeys = (HashMap<String, String>) other.caseInsensitiveKeys.clone();
        this.locale = other.locale;
    }


    // Implementation of java.util.Map

    /**
     * Size
     *
     * @return the int
     * @since 2023.1.1
     */
    @Override
    public int size() {
        return this.targetMap.size();
    }

    /**
     * Is empty
     *
     * @return the boolean
     * @since 2023.1.1
     */
    @Override
    public boolean isEmpty() {
        return this.targetMap.isEmpty();
    }

    /**
     * Contains key
     *
     * @param key key
     * @return the boolean
     * @since 2023.1.1
     */
    @Override
    public boolean containsKey(Object key) {
        return key instanceof String && this.caseInsensitiveKeys.containsKey(convertKey((String) key));
    }

    /**
     * Contains value
     *
     * @param value value
     * @return the boolean
     * @since 2023.1.1
     */
    @Override
    public boolean containsValue(Object value) {
        return this.targetMap.containsValue(value);
    }

    /**
     * Get
     *
     * @param key key
     * @return the v
     * @since 2023.1.1
     */
    @Override
    @Nullable
    public V get(Object key) {
        if (key instanceof String) {
            String caseInsensitiveKey = this.caseInsensitiveKeys.get(convertKey((String) key));
            if (caseInsensitiveKey != null) {
                return this.targetMap.get(caseInsensitiveKey);
            }
        }
        return null;
    }

    /**
     * Gets or default *
     *
     * @param key          key
     * @param defaultValue default value
     * @return the or default
     * @since 2023.1.1
     */
    @Override
    @Nullable
    public V getOrDefault(Object key, V defaultValue) {
        if (key instanceof String) {
            String caseInsensitiveKey = this.caseInsensitiveKeys.get(convertKey((String) key));
            if (caseInsensitiveKey != null) {
                return this.targetMap.get(caseInsensitiveKey);
            }
        }
        return defaultValue;
    }

    /**
     * Put
     *
     * @param key   key
     * @param value value
     * @return the v
     * @since 2023.1.1
     */
    @Override
    @Nullable
    public V put(String key, @Nullable V value) {
        String oldKey = this.caseInsensitiveKeys.put(convertKey(key), key);
        V oldKeyValue = null;
        if (oldKey != null && !oldKey.equals(key)) {
            oldKeyValue = this.targetMap.remove(oldKey);
        }
        V oldValue = this.targetMap.put(key, value);
        return oldKeyValue != null ? oldKeyValue : oldValue;
    }

    /**
     * Put all
     *
     * @param map map
     * @since 2023.1.1
     */
    @Override
    public void putAll(Map<? extends String, ? extends V> map) {
        if (map.isEmpty()) {
            return;
        }
        map.forEach(this::put);
    }

    /**
     * Put if absent
     *
     * @param key   key
     * @param value value
     * @return the v
     * @since 2023.1.1
     */
    @Override
    @Nullable
    public V putIfAbsent(String key, @Nullable V value) {
        String oldKey = this.caseInsensitiveKeys.putIfAbsent(convertKey(key), key);
        if (oldKey != null) {
            return this.targetMap.get(oldKey);
        }
        return this.targetMap.putIfAbsent(key, value);
    }

    /**
     * Compute if absent
     *
     * @param key             key
     * @param mappingFunction mapping function
     * @return the v
     * @since 2023.1.1
     */
    @Override
    @Nullable
    public V computeIfAbsent(String key, Function<? super String, ? extends V> mappingFunction) {
        String oldKey = this.caseInsensitiveKeys.putIfAbsent(convertKey(key), key);
        if (oldKey != null) {
            return this.targetMap.get(oldKey);
        }
        return this.targetMap.computeIfAbsent(key, mappingFunction);
    }

    /**
     * Remove
     *
     * @param key key
     * @return the v
     * @since 2023.1.1
     */
    @Override
    @Nullable
    public V remove(Object key) {
        if (key instanceof String) {
            String caseInsensitiveKey = removeCaseInsensitiveKey((String) key);
            if (caseInsensitiveKey != null) {
                return this.targetMap.remove(caseInsensitiveKey);
            }
        }
        return null;
    }

    /**
     * Clear
     *
     * @since 2023.1.1
     */
    @Override
    public void clear() {
        this.caseInsensitiveKeys.clear();
        this.targetMap.clear();
    }

    /**
     * Key set
     *
     * @return the set
     * @since 2023.1.1
     */
    @Override
    public Set<String> keySet() {
        Set<String> keySet = this.keySet;
        if (keySet == null) {
            keySet = new KeySet(this.targetMap.keySet());
            this.keySet = keySet;
        }
        return keySet;
    }

    /**
     * Values
     *
     * @return the collection
     * @since 2023.1.1
     */
    @Override
    public Collection<V> values() {
        Collection<V> values = this.values;
        if (values == null) {
            values = new Values(this.targetMap.values());
            this.values = values;
        }
        return values;
    }

    /**
     * Entry set
     *
     * @return the set
     * @since 2023.1.1
     */
    @Override
    public Set<Entry<String, V>> entrySet() {
        Set<Entry<String, V>> entrySet = this.entrySet;
        if (entrySet == null) {
            entrySet = new EntrySet(this.targetMap.entrySet());
            this.entrySet = entrySet;
        }
        return entrySet;
    }

    /**
     * Clone
     *
     * @return the linked case insensitive map
     * @since 2023.1.1
     */
    @Override
    @SuppressWarnings("checkstyle:SuperClone")
    public LinkedCaseInsensitiveMap<V> clone() {
        return new LinkedCaseInsensitiveMap<>(this);
    }

    /**
     * Equals
     *
     * @param other other
     * @return the boolean
     * @since 2023.1.1
     */
    @Override
    public boolean equals(@Nullable Object other) {
        return this == other || this.targetMap.equals(other);
    }

    /**
     * Hash code
     *
     * @return the int
     * @since 2023.1.1
     */
    @Override
    public int hashCode() {
        return this.targetMap.hashCode();
    }

    /**
     * To string
     *
     * @return the string
     * @since 2023.1.1
     */
    @Override
    public String toString() {
        return this.targetMap.toString();
    }


    // Specific to LinkedCaseInsensitiveMap

    /**
     * Return the locale used by this {@code LinkedCaseInsensitiveMap}.
     * Used for case-insensitive key conversion.
     *
     * @return the locale
     * @see #LinkedCaseInsensitiveMap(Locale)
     * @see #convertKey(String)
     * @since 4.3.10
     */
    public Locale getLocale() {
        return this.locale;
    }

    /**
     * Convert the given key to a case-insensitive key.
     * <p>The default implementation converts the key
     * to lower-case according to this Map's Locale.
     *
     * @param key the user-specified key
     * @return the key to use for storing
     * @see String#toLowerCase(Locale)
     * @since 2023.1.1
     */
    protected String convertKey(String key) {
        return key.toLowerCase(getLocale());
    }

    /**
     * Determine whether this map should remove the given eldest entry.
     *
     * @param eldest the candidate entry
     * @return {@code true} for removing it, {@code false} for keeping it
     * @since 2023.1.1
     */
    protected boolean removeEldestEntry(Map.Entry<String, V> eldest) {
        return false;
    }

    /**
     * Remove case insensitive key
     *
     * @param key key
     * @return the string
     * @since 2023.1.1
     */
    @Nullable
    private String removeCaseInsensitiveKey(String key) {
        return this.caseInsensitiveKeys.remove(convertKey(key));
    }


    /**
     * <p>Description: </p>
     *
     * @version 1.0.0
     * @date 2023.01.03 09:58
     * @since 2023.1.1
     */
    private class KeySet extends AbstractSet<String> {

        /** Delegate */
        private final Set<String> delegate;

        /**
         * Key set
         *
         * @param delegate delegate
         * @since 2023.1.1
         */
        KeySet(Set<String> delegate) {
            this.delegate = delegate;
        }

        /**
         * Size
         *
         * @return the int
         * @since 2023.1.1
         */
        @Override
        public int size() {
            return this.delegate.size();
        }

        /**
         * Contains
         *
         * @param o o
         * @return the boolean
         * @since 2023.1.1
         */
        @Override
        public boolean contains(Object o) {
            return this.delegate.contains(o);
        }

        /**
         * Iterator
         *
         * @return the iterator
         * @since 2023.1.1
         */
        @Override
        public Iterator<String> iterator() {
            return new KeySetIterator();
        }

        /**
         * Remove
         *
         * @param o o
         * @return the boolean
         * @since 2023.1.1
         */
        @Override
        public boolean remove(Object o) {
            return LinkedCaseInsensitiveMap.this.remove(o) != null;
        }

        /**
         * Clear
         *
         * @since 2023.1.1
         */
        @Override
        public void clear() {
            LinkedCaseInsensitiveMap.this.clear();
        }

        /**
         * Spliterator
         *
         * @return the spliterator
         * @since 2023.1.1
         */
        @Override
        public Spliterator<String> spliterator() {
            return this.delegate.spliterator();
        }

        /**
         * For each
         *
         * @param action action
         * @since 2023.1.1
         */
        @Override
        public void forEach(Consumer<? super String> action) {
            this.delegate.forEach(action);
        }
    }


    /**
     * <p>Description: </p>
     *
     * @version 1.0.0
     * @date 2023.01.03 09:58
     * @since 2023.1.1
     */
    private class Values extends AbstractCollection<V> {

        /** Delegate */
        private final Collection<V> delegate;

        /**
         * Values
         *
         * @param delegate delegate
         * @since 2023.1.1
         */
        Values(Collection<V> delegate) {
            this.delegate = delegate;
        }

        /**
         * Size
         *
         * @return the int
         * @since 2023.1.1
         */
        @Override
        public int size() {
            return this.delegate.size();
        }

        /**
         * Contains
         *
         * @param o o
         * @return the boolean
         * @since 2023.1.1
         */
        @Override
        public boolean contains(Object o) {
            return this.delegate.contains(o);
        }

        /**
         * Iterator
         *
         * @return the iterator
         * @since 2023.1.1
         */
        @Override
        public Iterator<V> iterator() {
            return new ValuesIterator();
        }

        /**
         * Clear
         *
         * @since 2023.1.1
         */
        @Override
        public void clear() {
            LinkedCaseInsensitiveMap.this.clear();
        }

        /**
         * Spliterator
         *
         * @return the spliterator
         * @since 2023.1.1
         */
        @Override
        public Spliterator<V> spliterator() {
            return this.delegate.spliterator();
        }

        /**
         * For each
         *
         * @param action action
         * @since 2023.1.1
         */
        @Override
        public void forEach(Consumer<? super V> action) {
            this.delegate.forEach(action);
        }
    }


    /**
     * <p>Description: </p>
     *
     * @version 1.0.0
     * @date 2023.01.03 09:58
     * @since 2023.1.1
     */
    private class EntrySet extends AbstractSet<Entry<String, V>> {

        /** Delegate */
        private final Set<Entry<String, V>> delegate;

        /**
         * Entry set
         *
         * @param delegate delegate
         * @since 2023.1.1
         */
        EntrySet(Set<Entry<String, V>> delegate) {
            this.delegate = delegate;
        }

        /**
         * Size
         *
         * @return the int
         * @since 2023.1.1
         */
        @Override
        public int size() {
            return this.delegate.size();
        }

        /**
         * Contains
         *
         * @param o o
         * @return the boolean
         * @since 2023.1.1
         */
        @Override
        public boolean contains(Object o) {
            return this.delegate.contains(o);
        }

        /**
         * Iterator
         *
         * @return the iterator
         * @since 2023.1.1
         */
        @Override
        public Iterator<Entry<String, V>> iterator() {
            return new EntrySetIterator();
        }

        /**
         * Remove
         *
         * @param o o
         * @return the boolean
         * @since 2023.1.1
         */
        @Override
        @SuppressWarnings("unchecked")
        public boolean remove(Object o) {
            if (this.delegate.remove(o)) {
                removeCaseInsensitiveKey(((Map.Entry<String, V>) o).getKey());
                return true;
            }
            return false;
        }

        /**
         * Clear
         *
         * @since 2023.1.1
         */
        @Override
        public void clear() {
            this.delegate.clear();
            caseInsensitiveKeys.clear();
        }

        /**
         * Spliterator
         *
         * @return the spliterator
         * @since 2023.1.1
         */
        @Override
        public Spliterator<Entry<String, V>> spliterator() {
            return this.delegate.spliterator();
        }

        /**
         * For each
         *
         * @param action action
         * @since 2023.1.1
         */
        @Override
        public void forEach(Consumer<? super Entry<String, V>> action) {
            this.delegate.forEach(action);
        }
    }


    /**
     * <p>Description: </p>
     *
     * @param <T> parameter
     * @version 1.0.0
     * @date 2023.01.03 09:58
     * @since 2023.1.1
     */
    private abstract class EntryIterator<T> implements Iterator<T> {

        /** Delegate */
        private final Iterator<Entry<String, V>> delegate;

        /** Last */
        @Nullable
        private Entry<String, V> last;

        /**
         * Entry iterator
         *
         * @since 2023.1.1
         */
        EntryIterator() {
            this.delegate = targetMap.entrySet().iterator();
        }

        /**
         * Next entry
         *
         * @return the entry
         * @since 2023.1.1
         */
        protected Entry<String, V> nextEntry() {
            Entry<String, V> entry = this.delegate.next();
            this.last = entry;
            return entry;
        }

        /**
         * Has next
         *
         * @return the boolean
         * @since 2023.1.1
         */
        @Override
        public boolean hasNext() {
            return this.delegate.hasNext();
        }

        /**
         * Remove
         *
         * @since 2023.1.1
         */
        @Override
        public void remove() {
            this.delegate.remove();
            if (this.last != null) {
                removeCaseInsensitiveKey(this.last.getKey());
                this.last = null;
            }
        }
    }


    /**
     * <p>Description: </p>
     *
     * @version 1.0.0
     * @date 2023.01.03 09:58
     * @since 2023.1.1
     */
    private class KeySetIterator extends EntryIterator<String> {

        /**
         * Next
         *
         * @return the string
         * @since 2023.1.1
         */
        @Override
        public String next() {
            return nextEntry().getKey();
        }
    }


    /**
     * <p>Description: </p>
     *
     * @version 1.0.0
     * @date 2023.01.03 09:58
     * @since 2023.1.1
     */
    private class ValuesIterator extends EntryIterator<V> {

        /**
         * Next
         *
         * @return the v
         * @since 2023.1.1
         */
        @Override
        public V next() {
            return nextEntry().getValue();
        }
    }


    /**
     * <p>Description: </p>
     *
     * @version 1.0.0
     * @date 2023.01.03 09:58
     * @since 2023.1.1
     */
    private class EntrySetIterator extends EntryIterator<Entry<String, V>> {

        /**
         * Next
         *
         * @return the entry
         * @since 2023.1.1
         */
        @Override
        public Entry<String, V> next() {
            return nextEntry();
        }
    }

}
