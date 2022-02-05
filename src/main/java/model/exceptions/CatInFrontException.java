package model.exceptions;

/**
 * This exception gets thrown when there's a cat in front of the direction in which the character looks, and he can't
 * step over it, or when the character wants to pick up a cat, even though there isn't one in front of him.
 *
 * @since 05.11.2021
 */

public class CatInFrontException extends Throwable {
}
