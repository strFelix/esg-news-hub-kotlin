package br.com.strfelix.esg_news_hub_kotlin.repository.user

import br.com.strfelix.esg_news_hub_kotlin.model.User

interface IUserRepository {
    fun saveUser(user: User)
    fun getUser(): User
    fun getUserByEmail(email: String): User?
    fun login(email: String, password: String): Boolean
}