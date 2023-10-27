package br.ufes.inf.nemo.vpzy;

/**
 * Exception throw when a template configuration is not valid.
 *
 * @author Igor Sunderhus e Silva
 * @version 0.0.1
 */
public class TemplateValidationException extends RuntimeException {
    public TemplateValidationException(final String s, final Exception e) {
        super(s, e);
    }
}
