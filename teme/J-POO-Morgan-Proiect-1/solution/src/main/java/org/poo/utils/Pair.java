package org.poo.utils;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@EqualsAndHashCode
@RequiredArgsConstructor
public final class Pair<K, V> {
    private final K value0;
    private final V value1;
}
