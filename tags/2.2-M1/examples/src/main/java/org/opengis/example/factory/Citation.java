package org.opengis.example.factory;

import java.util.Collection;

public interface Citation {
    String getISBN();
    Collection<? extends CitationDate> getDates();
}
