package com.chrisbaileydeveloper.bookshelf.web.util;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

public class UrlUtil {

    private static final Logger logger = LoggerFactory.getLogger(UrlUtil.class);

    private UrlUtil() {
        // intentionally empty
    }

    public static String encodeUrlPathSegment(final String pathSegment, final HttpServletRequest httpServletRequest) {

        String characterEncoding = httpServletRequest.getCharacterEncoding();

        if (characterEncoding == null) {
            characterEncoding = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }

        String ps = pathSegment;
        try {
            ps = UriUtils.encodePathSegment(pathSegment, characterEncoding);
        } catch (final UnsupportedEncodingException uee) {
            logger.warn("could not encode path segment", uee);
        }
        return ps;
    }
}
