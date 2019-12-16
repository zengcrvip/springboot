package com.example.demo.execl.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tuple2<T1, T2> {

    private T1 t1;

    private T2 t2;

    public boolean isNull() {
        return Objects.isNull(t1) && Objects.isNull(t2);
    }

}
