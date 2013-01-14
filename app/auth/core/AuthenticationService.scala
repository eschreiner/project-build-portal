package auth.core

trait AuthenticationService[UserID,UserInfo] {

  def authenticate(userId:UserID, password:String, rememberMe:Boolean):
	  	Either[AuthenticationFault, UserAuthenticatedEvent[UserInfo]]

  def authenticateWithCookie(userId:UserID, series:Long, token:Long):
	  	Either[AuthenticationFault, UserAuthenticatedWithTokenEvent[UserInfo]]
}
