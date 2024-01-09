package org.example.domain.exceptions;

import java.security.PublicKey;

public class ScyllaDbError extends RuntimeException{
    public ScyllaDbError(){
        super("Error processing DB.");
    }
}
