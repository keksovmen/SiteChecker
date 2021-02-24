package com.keksovmen.Pinger.Util;

import java.util.List;

public interface Observer<T> {

    public void observe(List<T> data);
}
