package com.qwanchi.cryptbuddy.DB

import androidx.room.*

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val email: String,
    val password: String // Encrypted
)

@Entity(
    primaryKeys = ["user_id", "password_id"],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["user_id"]
        ),
        ForeignKey(
            entity = Password::class,
            parentColumns = ["id"],
            childColumns = ["password_id"]
        )
    ]
)
data class UserPassword(
    val user_id: Int,
    val password_id: Int
)

@Entity
data class Password(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val website_url: String,
    val username: String,
    val password: String, // Encrypted
    val notes: String?, // Encrypted
    val last_updated: Long
)

// Data Access Objects (DAOs)
@Dao
interface UserDao {

    @Insert
    suspend fun insert(user: User): Long

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("SELECT * FROM User WHERE id = :id")
    fun getUserById(id: Int): User?

    @Query("SELECT * FROM User WHERE email = :email")
    fun getUserByEmail(email: String): User?
}

@Dao
interface UserPasswordDao {

    @Insert
    suspend fun insert(userPassword: UserPassword)

    @Delete
    suspend fun delete(userPassword: UserPassword)

    @Query("SELECT * FROM Password WHERE id IN (SELECT password_id FROM UserPassword WHERE user_id = :userId)")
    fun getPasswordsForUser(userId: Int): List<Password>
}

@Dao
interface PasswordDao {

    @Insert
    suspend fun insert(password: Password): Long

    @Update
    suspend fun update(password: Password)

    @Delete
    suspend fun delete(password: Password)

    @Query("SELECT * FROM Password WHERE id = :id")
    fun getPasswordById(id: Int): Password?
}