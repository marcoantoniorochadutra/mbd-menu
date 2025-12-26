package com.mbd.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageTranslateHelper {

    private static final String DEFAULT_ERROR = "RuntimeException.message";

    protected final MessageSource errorMessageSource;
    protected final MessageSource validationMessageSource;
    protected final MessageSource returnMessageSource;

    public MessageTranslateHelper(
            @Qualifier("errorMessageSource") MessageSource errorMessageSource,
            @Qualifier("validationMessageSource") MessageSource validationMessageSource,
            @Qualifier("returnMessageSource") MessageSource returnMessageSource) {
        this.errorMessageSource = errorMessageSource;
        this.validationMessageSource = validationMessageSource;
        this.returnMessageSource = returnMessageSource;
    }

    public String getErrorMessage(Exception ex, Object... args) {
        var code = String.format("%s.message", ex.getClass().getSimpleName());
        try {
            return this.errorMessageSource.getMessage(code, args, LocaleContextHolder.getLocale());
        } catch (Exception _) {
            log.info("{} - Could not get error message for {}", this.getClass().getSimpleName(), code);
            return this.errorMessageSource.getMessage(DEFAULT_ERROR, args, LocaleContextHolder.getLocale());
        }
    }

    public String getErrorMessage(String string, Object... args) {
        var code = String.format("%s.message", string);
        try {
            return this.errorMessageSource.getMessage(code, args, LocaleContextHolder.getLocale());
        } catch (Exception _) {
            log.info("{} - Could not error message for {}", this.getClass().getSimpleName(), code);
            return this.errorMessageSource.getMessage(DEFAULT_ERROR, args, LocaleContextHolder.getLocale());
        }
    }

    public String getValidationMessage(String code, Object... args) {
        return this.validationMessageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }

    public String getReturnMessage(String string, Object... args) {
            var code = string.replace("{", StringUtils.EMPTY).replace("}", StringUtils.EMPTY);
        try {
            return this.returnMessageSource.getMessage(code, args, LocaleContextHolder.getLocale());
        } catch (Exception _) {
            log.info("{} - Could not get return message for {}", this.getClass().getSimpleName(), code);
            return this.errorMessageSource.getMessage(DEFAULT_ERROR, args, LocaleContextHolder.getLocale());
        }
    }

}
