package kz.aphion.adverts.web.api.exceptions;

/**
 * Ошибка в случае если параметр есть но не соотвествует ожидаемым значениям
 * 
 * @author artem.demidovich
 *
 * Created at Aug 14, 2016
 */
public class IncorrectParameterValueException extends Exception {
	
	private static final long serialVersionUID = -1245006028651724309L;

	/**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public IncorrectParameterValueException() {
        super();
    }

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param   message   the detail message. The detail message is saved for
     *          later retrieval by the {@link #getMessage()} method.
     */
    public IncorrectParameterValueException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message and
     * cause.  <p>Note that the detail message associated with
     * {@code cause} is <i>not</i> automatically incorporated in
     * this exception's detail message.
     *
     * @param  message the detail message (which is saved for later retrieval
     *         by the {@link #getMessage()} method).
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link #getCause()} method).  (A <tt>null</tt> value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     * @since  1.4
     */
    public IncorrectParameterValueException(String message, Throwable cause) {
        super(message, cause);
    }
	
	
}
