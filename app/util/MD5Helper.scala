package util

/**
 * @author  Dr. Erich W. Schreiner - Software Design &amp; Consulting GmbH
 * @version 0.1.0.0
 * @since   0.1.0.0
 */
object MD5Helper {

    import ChecksumHelper._

	/**
	 * Calculate an MD5 hash from the input string.
	 * @param input the text
	 * @return the MD5 hash as 32 character hex string with dashes inserted every 8 characters
	 */
	def apply(input: String): String = checksum(input, "MD5")

	/**
	 * Calculate an MD5 hash from the input string and return the first 8 characters.
	 * @param input the input
	 * @return the MD5 token
	 */
	def token(input: String): String = apply(input).substring(0, 8)

}