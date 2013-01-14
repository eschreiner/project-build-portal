package util

/**
 * @author  Dr. Erich W. Schreiner - Software Design &amp; Consulting GmbH
 * @version 0.1.0.0
 * @since   0.1.0.0
 */
object ChecksumHelper {

	def convertToHex(data: Array[Byte]): String  = convertToHex(data, false)

	def convertToHex(data: Array[Byte], insertSlashes: Boolean): String = {
		val buf = new StringBuffer();
		for (i <- 0 until data.length) {
			if (insertSlashes && i > 0 && i % 4 == 0) buf.append('-')
			buf.append("%02X".format(data(i)))
		}
		buf.toString()
	}

	import java.security._

	def checksum(input: String, key: String): String = {
		try {
			val md = MessageDigest.getInstance(key)
			val bytes = input.getBytes()
			md.update(bytes, 0, bytes.length)
			convertToHex(md.digest())
		} catch {
		    case e: NoSuchAlgorithmException => e.getMessage()
		}
	}

}