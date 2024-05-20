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
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Password::class,
            parentColumns = ["id"],
            childColumns = ["password_id"],
            onDelete = ForeignKey.CASCADE
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
    var website_url: String,
    var username: String,
    var password: String, // Encrypted
    var notes: String?, // Encrypted
    var last_updated: Long
)

// Data Access Objects (DAOs)
@Dao
interface UserDao {

    @Insert
    fun insert(user: User): Long

    @Update
    fun update(user: User)

    @Delete
    fun delete(user: User)

    @Query("SELECT * FROM User WHERE id = :id")
    fun getUserById(id: Int): User?

    @Query("SELECT * FROM User WHERE email = :email")
    fun getUserByEmail(email: String): User?

    @Query("SELECT COUNT(*) FROM User")
    fun getUserCount(): Int


}

@Dao
interface UserPasswordDao {

    @Insert
    fun insert(userPassword: UserPassword)

    @Delete
    fun delete(userPassword: UserPassword)

    @Query("SELECT * FROM Password WHERE id IN (SELECT password_id FROM UserPassword WHERE user_id = :userId)")
    fun getPasswordsForUser(userId: Int): List<Password>
}

@Dao
interface PasswordDao {

    @Insert
    fun insert(password: Password): Long

    @Update
    fun update(password: Password)

    @Delete
    fun delete(password: Password)

    @Query("SELECT * FROM Password WHERE id = :id")
    fun getPasswordById(id: Int): Password?
    @Query("SELECT COUNT(*) FROM Password")
    fun getPasswordCount(): Int
}