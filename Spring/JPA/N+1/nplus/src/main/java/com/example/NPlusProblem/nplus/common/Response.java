package com.example.NPlusProblem.nplus.common;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Response<T> {

    T content;
}
