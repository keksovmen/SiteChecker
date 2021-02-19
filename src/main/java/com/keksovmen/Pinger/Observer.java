package com.keksovmen.Pinger;

import java.util.List;

public interface Observer<T> {

    void observe(List<T> data);
}
