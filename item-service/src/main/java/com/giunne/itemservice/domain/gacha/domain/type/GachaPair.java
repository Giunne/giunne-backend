package com.giunne.itemservice.domain.gacha.domain.type;

public class GachaPair<K, V> {

    public K key;
    public V weight;

    public GachaPair(K key, V weight) {
        this.key = key;
        this.weight = weight;
    }
}