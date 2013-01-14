package auth.core

trait UserInfoService[UserID, UserInfo] {

  def lookup(userId:UserID): Option[UserInfo]

}
