package com.sourabh.appnews.core;

interface Filter<T,E> {
    public boolean isMatchedCategory(T object, E text);
}