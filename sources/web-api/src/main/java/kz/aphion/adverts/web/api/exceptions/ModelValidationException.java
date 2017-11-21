package kz.aphion.adverts.web.api.exceptions;

/**
 * Исключение для всех ошибок валидации модели данных. 
 * В основном данных которые поступают на сервер с клиента.
 * @author artem.demidovich
 *
 * Created at Nov 20, 2017
 */
public class ModelValidationException extends WebAPIException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6136096143477296577L;

	
	/**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public ModelValidationException() {
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
    public ModelValidationException(String message) {
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
    public ModelValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
