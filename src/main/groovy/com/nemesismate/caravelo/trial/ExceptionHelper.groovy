package com.nemesismate.caravelo.trial

/**
 * @author NemesisMate
 */
final class ExceptionHelper {

    private ExceptionHelper() { }

    static final Exception unimplemented() {
        throw new UnsupportedOperationException("Not implemented YET")
    }

}
