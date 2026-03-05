package br.com.strfelix.esg_news_hub_kotlin.repository.user

import android.content.Context
import br.com.strfelix.esg_news_hub_kotlin.dao.EsgNewsDatabase
import br.com.strfelix.esg_news_hub_kotlin.model.User

class UserRepository(context: Context): IUserRepository {

    private val recipeDatabase = EsgNewsDatabase
        .getDatabase(context).userDao()

    override fun saveUser(user: User) {
        recipeDatabase.save(user)
    }

    override fun getUser(): User {
        return recipeDatabase.getUserById(1) ?: User()
    }

    override fun getUserByEmail(email: String): User? {
        return recipeDatabase.getUserByEmail(email)
    }

    override fun login(email: String, password: String): Boolean {
        val user = recipeDatabase.login(email, password)
        return user != null
    }
}