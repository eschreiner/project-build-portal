package auth.core

trait UserIdConverter[UserID] {

  def apply(userId:String): UserID

}
