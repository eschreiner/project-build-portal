package util

/**
 * @author  Dr. Erich W. Schreiner - Software Design &amp; Consulting GmbH
 * @version 0.1.0.0
 * @since   0.1.0.0
 */
object RandomString {

	private val charset = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"

	import java.util.Random

    /**
     * Creates a random string of specific length.
     * @param length the length
     * @return the random string
     */
    def apply(length: Int): String = {
        val rand = new Random(System.currentTimeMillis())
        val sb = new StringBuffer()
        for (i <- 0 until length) {
            val pos = rand.nextInt(charset.length())
            sb.append(charset.charAt(pos))
        }
        sb.toString()
    }

}