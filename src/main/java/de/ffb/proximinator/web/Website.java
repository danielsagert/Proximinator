package de.ffb.proximinator.web;

import de.ffb.proximinator.interceptor.Interceptor;
import de.ffb.proximinator.interceptor.JSInjector;
import de.ffb.proximinator.interceptor.SearchAndReplace;

public enum Website {
    BEAMERWAND(
            "http://beamerwand.petafuel.de/index2.php",
            "http://beamerwand.petafuel.de",
            "beamerwand",
            new Class[]{
                    SearchAndReplace.class,
                    JSInjector.class
            }
    );

    private final String url;
    private final String domain;
    private final String context;
    private final Class<? extends Interceptor>[] interceptorStack;

    Website(String url, String domain, String context, Class<? extends Interceptor>[] interceptorStack) {
        this.url = url;
        this.domain = domain;
        this.context = context;
        this.interceptorStack = interceptorStack;
    }

    public String getUrl() {
        return url;
    }

    public String getDomain() {
        return domain;
    }

    public String getContext() {
        return context;
    }

    public Class<? extends Interceptor>[] getInterceptorStack() {
        return interceptorStack;
    }
}
