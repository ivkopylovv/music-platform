package com.kopylov.musicplatform.helper;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Sort;

@UtilityClass
public class SortHelper {

    public Sort getSortScript(boolean asc, String attribute) {
        return asc ? Sort.by(attribute).ascending() : Sort.by(attribute).descending();
    }
}
