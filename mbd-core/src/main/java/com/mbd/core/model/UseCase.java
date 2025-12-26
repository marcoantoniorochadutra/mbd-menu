package com.mbd.core.model;

public interface UseCase<I, O> {

    O execute(I cmd);
}
