package de.ffb.proximinator.interceptor;

import de.ffb.proximinator.web.Website;

public abstract class Interceptor {
    protected Website website;
    protected String content;

    public Interceptor(Website website, String content) {
        this.website = website;
        this.content = content;
    }

    public abstract String intercept();
}
